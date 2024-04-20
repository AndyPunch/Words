package com.example.words.data.repository

import android.content.Context
import com.example.words.data.helpers.PreferenceDataStoreHelper
import com.example.words.domain.entity.Word
import com.example.words.domain.repository.WordsRepository
import com.example.words.utils.AppConstants

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val wordsDao: com.example.words.data.db.WordsDao,
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
) : WordsRepository {

    override suspend fun insertWords(words: List<Word>): List<Long> {
        return wordsDao.insertOnStart(words)
    }

    override suspend fun isDbCreated(): Flow<Boolean> {
        return preferenceDataStoreHelper.getPreference(AppConstants.IS_DB_CREATED_KEY, false)
    }

    override suspend fun setDbCreated() {
        preferenceDataStoreHelper.putPreference(AppConstants.IS_DB_CREATED_KEY, true)
    }

    override suspend fun getWord(): Word {

        return wordsDao.getWord()
    }

    override suspend fun getCount(): String {
        val count = wordsDao.getCount()
        val nonStudiedCount = count.nonStudiedCount
        val studiedCount = count.studiedCount
        val allCount = nonStudiedCount + studiedCount

        return "All: $allCount; Studied: $studiedCount; Remain: $nonStudiedCount"
    }

    override suspend fun updateWord(word: Word) {
        wordsDao.updateWord(word)
    }

    override suspend fun searchWords(search: String): List<Word> {
        return wordsDao.searchWords(search)
    }

    fun getWordsFromAssets(): List<String> {
        val str =
            ctx.assets.open(com.example.words.data.repository.WordsRepositoryImpl.Companion.WORDS_TXT_FILE)
                .bufferedReader().use {
                    it.readText()
                }
        return stringToWords(str)
    }

    private fun stringToWords(s: String) = s.trim().split("\\s+".toRegex())


    companion object {
        const val WORDS_TXT_FILE = "words.txt"
    }

}