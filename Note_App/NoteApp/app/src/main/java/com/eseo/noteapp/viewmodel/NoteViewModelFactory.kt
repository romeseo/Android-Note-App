package com.eseo.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eseo.noteapp.model.repository.NoteRepository

// Factory pour créer et fournir une instance de NoteViewModel avec le repository
class NoteViewModelFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {

    // Fonction pour créer une instance de ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Vérifie si la classe fournie est une instance de NoteViewModel
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            // Crée et retourne une nouvelle instance de NoteViewModel avec le repository
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepository) as T
        }
        // Lance une exception si la classe fournie n'est pas une instance de NoteViewModel
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
