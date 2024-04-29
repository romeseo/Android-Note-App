package com.eseo.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eseo.noteapp.model.entity.Note
import com.eseo.noteapp.model.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel pour gérer les données liées à l'affichage des notes
class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    // LiveData pour observer les changements dans la liste des notes
    val notesLiveData: LiveData<List<Note>> = noteRepository.mAllNotes

    // Fonction pour insérer une nouvelle note dans la base de données via le repository
    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insertNote(note)
    }

    // Fonction pour supprimer une note existante de la base de données via le repository
    fun deleteNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(id)
    }

    // Fonction pour mettre à jour une note existante dans la base de données via le repository
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(note)
    }
}
