package com.eseo.noteapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eseo.noteapp.R
import com.eseo.noteapp.databinding.ActivityNoteBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.eseo.noteapp.NoteApplication
import com.eseo.noteapp.model.entity.Note
import com.eseo.noteapp.view.adapter.NoteAdapter
import com.eseo.noteapp.viewmodel.NoteViewModel
import com.eseo.noteapp.viewmodel.NoteViewModelFactory


// Activité principale pour afficher la liste des notes et ajouter de nouvelles notes
class NoteActivity : AppCompatActivity() {

    private val newNoteActivityRequestCode = 1

    // ViewModel associé à cette activité
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    // Binding pour lier le layout de l'activité
    private lateinit var binding: ActivityNoteBinding

    // Adapter pour afficher la liste des notes dans le RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration du layout de l'activité avec DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)

        // Initialisation de l'adapter avec le ViewModel
        noteAdapter = NoteAdapter(noteViewModel)

        // Configuration du RecyclerView pour afficher la liste des notes
        setNoteRecycler()

        // Observer pour écouter les changements dans la liste des notes
        noteViewModel.notesLiveData.observe(this, Observer { notes ->
            noteAdapter.loadNotes(notes)
        })

        // Gestion du clic sur le bouton "Ajouter une note"
        binding.addNewNote.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }
    }

    // Configuration du RecyclerView
    private fun setNoteRecycler() {
        binding.noteRecycler.apply {
            adapter = noteAdapter
            // Ici le spanCount est modifié pour 1 afin d'avoir des notes rectangles dans l'application
            layoutManager = GridLayoutManager(applicationContext, 1)
            setHasFixedSize(true)
        }
    }

    // Gestion du résultat de l'activité d'ajout de note
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        // Vérification si le résultat provient de l'activité d'ajout de note
        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {

            // Récupération de la note ajoutée à partir de l'intent de réponse
            val note = intentData?.getSerializableExtra(AddActivity.EXTRA_REPLY) as Note

            // Insertion de la note dans la base de données via le ViewModel
            noteViewModel.insertNote(note)

        } else {
            // Affichage d'un message si l'ajout de note est annulé
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}
