package com.example.words.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val word: String,
    var isStudied: Boolean = false,
)