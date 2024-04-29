package com.eseo.noteapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.eseo.noteapp.model.entity.Note

// Définition d'une interface DAO (Data Access Object) pour interagir avec la base de données
@Dao
interface NoteDao {
    // Annotation @Insert pour insérer une nouvelle note dans la base de données
    @Insert
    fun insertNote(note: Note)

    // Annotation @Query pour supprimer une note de la base de données en fonction de son ID
    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    // Annotation @Query pour supprimer toutes les notes de la base de données
    @Query("DELETE FROM note_table")
    fun deleteAll()

    // Annotation @Query pour récupérer toutes les notes de la base de données sous forme de LiveData
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): LiveData<List<Note>>

    // Annotation @Update pour mettre à jour une note dans la base de données
    @Update(
        entity = Note::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun updateNote(note: Note)
}
