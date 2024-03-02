package com.example.words.presentation.main

import com.example.words.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val navigator: Navigator
) : com.example.words.base.BaseViewModel() {
    fun getNavigationState(): Navigator {

        return navigator
    }

}