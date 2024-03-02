package com.example.words.domain.repository

import com.example.words.domain.entity.Word
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    suspend fun insertWords(words: List<Word>): List<Long>

    suspend fun isDbCreated(): Flow<Boolean>

    suspend fun setDbCreated()

    suspend fun getWord(): Word

    suspend fun getCount(): String

    suspend fun updateWord(word: Word)

    suspend fun searchWords(search: String): List<Word>

}