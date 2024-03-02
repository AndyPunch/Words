package com.example.words.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.words.R
import com.example.words.domain.entity.TopBarState
import com.example.words.navigation.AppNavGraph
import com.example.words.navigation.NavigationState
import com.example.words.presentation.search.SearchScreen
import com.example.words.presentation.webView.WebViewScreen
import com.example.words.presentation.word.WordScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val navigationState = viewModel.getNavigationState().getNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    var topBarState by remember { mutableStateOf(TopBarState()) }
    var isBottomBarVisible by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {

            TopBar(topBarState = topBarState)
        },
        bottomBar = {
            BottomNavigationBar(navigationState, navBackStackEntry, isBottomBarVisible)


        }

    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            wordScreenContent = {
                WordScreen(paddingValues, navigationState,
                    onComposingTopBar = {
                        topBarState = it
                        isBottomBarVisible = true
                    })
            },
            webViewScreenContent = {

                WebViewScreen(
                    it,
                    navigationState.navHostController,
                    onComposingTopBar = {
                        topBarState = it
                        isBottomBarVisible = false
                    }
                )
            },
            searchScreenContent = {
                SearchScreen(
                    navigationState.navHostController,
                    onComposingTopBar = {
                        topBarState = it
                        isBottomBarVisible = true
                    },
                    onSearchResultClick = {
                        navigationState.navigateToWebview(it.word)
                    }
                )
            },
            statisticScreenContent = {

            }
        )
    }


}

@Composable
fun BottomNavigationBar(
    navigationState: NavigationState,
    navBackStackEntry: NavBackStackEntry?,
    isBottomVisible: Boolean = true
) {

    AnimatedVisibility(
        visible = isBottomVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(backgroundColor = Color.Gray) {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Search,
                    NavigationItem.Statistics
                )
                items.forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = colorResource(R.color.orange_1),
                        unselectedContentColor = colorResource(R.color.white)
                    )
                }
            }
        })
}


@Composable
fun TopBar(topBarState: TopBarState) {
    TopAppBar(
        title = {
            Text(text = topBarState.topBarTitle)
        },
        navigationIcon = topBarState.navigationIcon,
        backgroundColor = Color.Gray,
        contentColor = colorResource(id = R.color.orange_1)
    )
}








