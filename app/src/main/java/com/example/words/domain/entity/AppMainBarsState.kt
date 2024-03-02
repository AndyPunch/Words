package com.example.words.domain.entity

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class TopBarState(
    var topBarTitle: String = "",
    var navigationIcon: @Composable (() -> Unit)? = null,
    var actions: (@Composable RowScope.() -> Unit)? = null
)