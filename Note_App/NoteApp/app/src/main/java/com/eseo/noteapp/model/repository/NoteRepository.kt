package com.eseo.noteapp.model.repository

import androidx.lifecycle.LiveData
import com.eseo.noteapp.model.dao.NoteDao
import com.eseo.noteapp.model.entity.Note

// Repository pour gérer les opérations sur les notes dans la base de données
class NoteRepository(private val mNoteDao: NoteDao?) {

    // LiveData contenant la liste de toutes les notes
    val mAllNotes: LiveData<List<Note>> = mNoteDao!!.getAllNotes()

    // Fonction pour insérer une nouvelle note dans la base de données
    suspend fun insertNote(note: Note) {
        mNoteDao!!.insertNote(note)
    }

    // Fonction pour supprimer une note de la base de données en fonction de son ID
    suspend fun deleteNote(id: Int) {
        mNoteDao!!.deleteNote(id)
    }

    // Fonction pour mettre à jour une note dans la base de données
    suspend fun updateNote(note: Note) {
        mNoteDao!!.updateNote(note)
    }
}
