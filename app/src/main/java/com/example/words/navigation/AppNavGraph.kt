package com.example.words.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    wordScreenContent: @Composable () -> Unit,
    webViewScreenContent: @Composable (String) -> Unit,
    searchScreenContent: @Composable () -> Unit,
    statisticScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        wordScreenNavGraph(
            wordScreenContent = wordScreenContent,
            webViewScreenContent = webViewScreenContent
        )
        composable(Screen.Search.route) {
            searchScreenContent()
        }

        composable(Screen.Statistics.route) {
            statisticScreenContent()
        }
    }
}
