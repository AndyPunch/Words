package com.example.words.presentation.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.words.data.mappers.WordsMapper.toWord
import com.example.words.data.states.SubmitStatus
import com.example.words.domain.entity.Word
import com.example.words.navigation.NavigationState
import com.example.words.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val wordRepositoryImpl: com.example.words.data.repository.WordsRepositoryImpl,
    val navigator: Navigator
) : com.example.words.base.BaseViewModel() {
    val submitStatus = MutableLiveData<SubmitStatus>(SubmitStatus.ReadyToSubmit)
    val isNextScreen = MutableLiveData<Boolean>(false)

    fun setNavigationState(navigationState: NavigationState) {
        navigator.setNavigationState(navigationState)
    }

    fun getData() {
        viewModelScope.launch {
            submitStatus.value = SubmitStatus.InProgress
            try {
                wordRepositoryImpl.isDbCreated().collectLatest {
                    if (it) {
                        setNextScreen(it)
                    } else {
                        val listStrWords = wordRepositoryImpl.getWordsFromAssets()
                        val listObWords = listStrWords.map {
                            it.toWord(it)
                        }
                        insertWords(listObWords)
                    }
                }

            } catch (e: Exception) {
                submitStatus.value = SubmitStatus.Error(e.toString())
            }
        }
    }


    private fun insertWords(listWords: List<Word>) {
        viewModelScope.launch {
            try {
                val listIds = wordRepositoryImpl.insertWords(listWords)
                if (listIds.isNotEmpty() && listIds[0] == 1.toLong()) {
                    wordRepositoryImpl.setDbCreated()
                    setNextScreen(true)
                } else {
                    submitStatus.value =
                        SubmitStatus.Error("Insertion Words error")
                }
            } catch (e: Exception) {
                submitStatus.value = SubmitStatus.Error(e.toString())
            }
        }
    }

    private fun setNextScreen(isNext: Boolean) {
        submitStatus.value = SubmitStatus.Success
        isNextScreen.value = isNext
    }

}

