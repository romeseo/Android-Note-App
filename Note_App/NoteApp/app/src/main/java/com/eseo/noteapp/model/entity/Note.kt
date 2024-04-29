package com.eseo.noteapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Annotation @Entity indiquant que cette classe représente une table dans la base de données
@Entity(tableName = "note_table")
data class Note(
    // Annotation @PrimaryKey indiquant que cette propriété est la clé primaire de la table
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    // Annotation @ColumnInfo pour spécifier le nom de la colonne dans la table de la base de données
    @ColumnInfo(name = "title")
    var mNoteTitle: String,

    // Annotation @ColumnInfo pour spécifier le nom de la colonne dans la table de la base de données
    @ColumnInfo(name = "content")
    var mNoteContent: String
) : Serializable
