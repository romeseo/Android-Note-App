package com.eseo.noteapp.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eseo.noteapp.NoteApplication
import com.eseo.noteapp.R
import com.eseo.noteapp.databinding.ActivityUpdateBinding
import com.eseo.noteapp.model.entity.Note
import com.eseo.noteapp.viewmodel.NoteViewModel
import com.eseo.noteapp.viewmodel.NoteViewModelFactory

// Activité pour mettre à jour une note existante
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    // ViewModel associé à cette activité
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration du layout de l'activité avec DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update)

        // Récupération de la note à mettre à jour à partir de l'intent
        val note: Note = intent.getSerializableExtra("UPDATE") as Note

        // Remplissage des champs de titre et de contenu avec les valeurs de la note
        binding.titleEdit.setText(note.mNoteTitle)
        binding.contentEdit.setText(note.mNoteContent)

        // Gestion du clic sur le bouton de confirmation
        binding.confirmButton.setOnClickListener {
            // Mise à jour des valeurs de titre et de contenu de la note
            note.mNoteTitle = binding.titleEdit.text.toString()
            note.mNoteContent = binding.contentEdit.text.toString()

            // Appel de la fonction de mise à jour de la note via le ViewModel
            noteViewModel.updateNote(note)

            // Fermeture de l'activité
            finish()
        }
    }
}
