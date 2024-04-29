package com.eseo.noteapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eseo.noteapp.R
import com.eseo.noteapp.model.entity.Note
import com.eseo.noteapp.databinding.ActivityAddBinding

// Activity pour ajouter une nouvelle note
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Utiliser DataBindingUtil pour lier le layout à cette activité
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        // Associer un écouteur de clic au bouton de confirmation
        binding.confirmButton.setOnClickListener {

            // Créer un intent de réponse
            val replyIntent = Intent()

            // Vérifier si les champs de titre et de contenu sont vides
            if (TextUtils.isEmpty(binding.titleEdit.text) || TextUtils.isEmpty(binding.contentEdit.text)) {
                // Si l'un des champs est vide, envoyer un résultat annulé à l'activité appelante
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                // Si les champs ne sont pas vides, créer une nouvelle note avec les données saisies
                val note = Note(
                    mNoteTitle = binding.titleEdit.text.toString(),
                    mNoteContent = binding.contentEdit.text.toString()
                )

                // Ajouter la nouvelle note en tant qu'extra à l'intent de réponse
                replyIntent.putExtra(EXTRA_REPLY, note)

                // Envoyer un résultat OK avec l'intent de réponse à l'activité appelante
                setResult(Activity.RESULT_OK, replyIntent)
            }

            // Fermer cette activité
            finish()
        }
    }

    // Compagnon objet pour définir la clé de l'extra utilisé pour passer la note à l'activité appelante
    companion object {
        val EXTRA_REPLY: String = "com.eseo.android.noteapp.REPLY"
    }
}
