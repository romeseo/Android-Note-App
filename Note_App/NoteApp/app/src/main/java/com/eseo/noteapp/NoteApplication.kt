package com.eseo.noteapp

import android.app.Application
import com.eseo.noteapp.model.NoteDatabase
import com.eseo.noteapp.model.repository.NoteRepository

// Classe d'application pour initialiser la base de données et le repository
class NoteApplication : Application() {

    // Lazy initialization de la base de données
    val database by lazy { NoteDatabase.getNoteDatabase(this) }

    // Lazy initialization du repository avec le DAO de la base de données
    val repository by lazy { NoteRepository(database.getNoteDAO()) }
}
