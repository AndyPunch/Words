package com.example.words.domain.entity

import androidx.room.ColumnInfo

data class WordsCount(

    @ColumnInfo(name = "studied_count")
    val studiedCount: Int,

    @ColumnInfo(name = "non_studied_count")
    val nonStudiedCount: Int
)