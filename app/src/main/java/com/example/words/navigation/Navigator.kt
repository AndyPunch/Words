package com.example.words.navigation

object Navigator {
    private lateinit var navigationState: NavigationState

    fun setNavigationState(state: NavigationState) {
        navigationState = state
    }

    fun getNavigationState(): NavigationState {
        return navigationState
    }
}