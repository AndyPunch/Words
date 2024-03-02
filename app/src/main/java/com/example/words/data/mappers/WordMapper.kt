package com.example.words.data.mappers

import com.example.words.domain.entity.Word


object WordsMapper {
    fun String.toWord(str: String): Word {

        val word = Word(word = str)

        return word
    }
}