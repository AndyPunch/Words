package com.example.words.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.wordScreenNavGraph(
    wordScreenContent: @Composable () -> Unit,
    webViewScreenContent: @Composable (String) -> Unit
) {
    navigation(
        startDestination = Screen.Word.route,
        route = Screen.Home.route
    ) {
        composable(Screen.Word.route) {
            wordScreenContent()
        }
        composable(
            route = Screen.WebView.route,
            arguments = listOf(
                navArgument(Screen.KEY_WORD) {
                    type = NavType.StringType
                }
            )

        ) {
            val word = it.arguments?.getString(Screen.KEY_WORD) ?: ""
            webViewScreenContent(word)
        }
    }
}