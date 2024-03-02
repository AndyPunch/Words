package com.example.words.presentation.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.words.base.BaseViewModel
import com.example.words.data.repository.WordsRepositoryImpl
import com.example.words.domain.entity.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wordsRepositoryImpl: WordsRepositoryImpl
) : BaseViewModel() {

    private val _searchData = MutableLiveData<List<Word>>(mutableListOf())
    val searchData: LiveData<List<Word>> get() = _searchData

    fun searchWords(search: String) {
        if (search.isBlank()) {
            _searchData.value = mutableListOf()
            return
        }
        viewModelScope.launch {
            try {
                _searchData.value = wordsRepositoryImpl.searchWords(search)
            } catch (e: Exception) {
            }
        }
    }


}