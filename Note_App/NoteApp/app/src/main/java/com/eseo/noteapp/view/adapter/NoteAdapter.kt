package com.eseo.noteapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eseo.noteapp.R
import com.eseo.noteapp.model.entity.Note
import com.eseo.noteapp.view.activity.UpdateActivity
import com.eseo.noteapp.viewmodel.NoteViewModel

// Adapter pour afficher les notes dans un RecyclerView
class NoteAdapter(
    private val noteViewModel: NoteViewModel
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Liste des notes à afficher
    private lateinit var notes: MutableList<Note>

    init {
        notes = mutableListOf()
    }

    // Crée un ViewHolder en fonction du type de vue à afficher
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Crée une vue pour chaque élément de la liste en utilisant le layout 'note_item'
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(noteView)
    }

    // Lie les données de la note à l'élément de vue dans le ViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notes[position])
    }

    // Retourne le nombre total d'éléments dans la liste de notes
    override fun getItemCount(): Int {
        return notes.size
    }

    // Charge une liste de notes dans l'adapter
    fun loadNotes(notes: List<Note>?) {
        this.notes = notes as MutableList<Note>
        notifyDataSetChanged()
    }

    // ViewHolder pour chaque élément de la liste de notes
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var noteTitle: TextView
        private var noteContent: TextView
        private var editNoteImage: ImageView
        private var deleteNoteImage: ImageView

        init {
            // Initialisation des vues dans le ViewHolder
            noteTitle = itemView.findViewById(R.id.noteTitle)
            noteContent = itemView.findViewById(R.id.noteContent)
            editNoteImage = itemView.findViewById(R.id.editNoteImage)
            deleteNoteImage = itemView.findViewById(R.id.deleteNoteImage)
        }

        // Lie les données de la note à l'élément de vue dans le ViewHolder
        fun bindNote(note: Note) {
            noteContent.text = note.mNoteContent
            noteTitle.text = note.mNoteTitle

            // Gestion du clic sur l'icône d'édition de note
            editNoteImage.setOnClickListener {
                // Crée une intention pour démarrer UpdateActivity avec les données de la note
                val context: Context = itemView.context
                val noteIntent = Intent(context, UpdateActivity::class.java)
                noteIntent.putExtra("UPDATE", note)
                context.startActivity(noteIntent)
            }

            // Gestion du clic sur l'icône de suppression de note
            deleteNoteImage.setOnClickListener {
                // Supprime la note via le ViewModel
                noteViewModel.deleteNote(note.id)
            }
        }
    }
}
