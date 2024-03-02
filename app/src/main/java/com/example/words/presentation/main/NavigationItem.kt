package com.example.words.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.words.R
import com.example.words.navigation.Screen


sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    object Home : NavigationItem(
        screen = Screen.Word,
        titleResId = R.string.navigation_item_word,
        icon = Icons.Outlined.Home
    )

    object Search : NavigationItem(
        screen = Screen.Search,
        titleResId = R.string.navigation_item_search,
        icon = Icons.Outlined.Search
    )

    object Statistics : NavigationItem(
        screen = Screen.Statistics,
        titleResId = R.string.navigation_item_statistics,
        icon = Icons.Outlined.List
    )

}
