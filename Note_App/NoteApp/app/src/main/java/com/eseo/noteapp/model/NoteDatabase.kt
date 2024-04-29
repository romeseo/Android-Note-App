package com.eseo.noteapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eseo.noteapp.model.dao.NoteDao
import com.eseo.noteapp.model.entity.Note
import java.util.concurrent.Executors

// Annotation @Database pour définir la base de données
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    // Méthode abstraite pour obtenir l'interface DAO associée à la base de données
    abstract fun getNoteDAO(): NoteDao

    companion object {

        // Instance unique de la base de données
        @Volatile
        private var NOTE_INSTANCE: NoteDatabase? = null

        // Nombre de threads pour l'écriture dans la base de données
        private const val NUMBER_OF_THREADS = 4

        // Executor pour l'écriture dans la base de données sur plusieurs threads
        val databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        // Fonction pour obtenir une instance de la base de données
        fun getNoteDatabase(context: Context): NoteDatabase {
            if (NOTE_INSTANCE == null) {
                // Créer une nouvelle instance de la base de données si elle n'existe pas encore
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "noteDB"
                )
                    .addCallback(RoomDatabaseCallback) // Ajouter un callback pour peupler la base de données
                    .build()
                NOTE_INSTANCE = instance
            }
            return NOTE_INSTANCE!!
        }

        // Callback pour peupler la base de données lors de sa création
        val RoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Bloc de code exécuté lors de la création de la base de données
                databaseWriteExecutor.execute {
                    // Remplir la base de données en arrière-plan avec des données de test
                    val dao = NOTE_INSTANCE!!.getNoteDAO()
                    dao!!.deleteAll()
                    var note =
                        Note(0, "First Note", "test")
                    dao.insertNote(note)
                    note = Note(0, "Title Test", "This is content")
                    dao.insertNote(note)
                }
            }
        }
    }
}
