package com.example.words.presentation.word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.words.base.BaseViewModel
import com.example.words.data.repository.WordsRepositoryImpl
import com.example.words.domain.entity.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordsRepositoryImpl: WordsRepositoryImpl
) : BaseViewModel() {
    val wordData = MutableLiveData<Word>()
    val countData = MutableLiveData<String>()
    var isWordOnStartSet = false

    fun getWord() {
        if (!isWordOnStartSet) {
            updateWord()
            isWordOnStartSet = true
        }
    }

    fun updateWord() {
        viewModelScope.launch {
            try {
                val word = wordsRepositoryImpl.getWord()
                wordData.value = word
                getCount()
            } catch (e: Exception) {

            }
        }
    }

    fun updateWordInDb(word: Word) {
        viewModelScope.launch {
            try {
                wordsRepositoryImpl.updateWord(word)
                updateWord()
            } catch (e: Exception) {

            }
        }
    }

    private fun getCount() {
        viewModelScope.launch {
            try {
                val countStr = wordsRepositoryImpl.getCount()
                countData.value = countStr
            } catch (e: Exception) {

            }
        }
    }
}