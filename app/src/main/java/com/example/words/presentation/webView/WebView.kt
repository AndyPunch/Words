package com.example.words.presentation.webView

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.words.domain.entity.TopBarState

@Composable
fun WebViewScreen(
    word: String = "",
    navHostController: NavHostController,
    onComposingTopBar: (TopBarState) -> Unit
) {
    //BackHandler(onBack = onBackPressed)
    LaunchedEffect(key1 = true) {
        onComposingTopBar(
            TopBarState(
                topBarTitle = word,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        )

    }

    Scaffold(
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            factory = {
                WebView(it).apply {
                    webViewClient = WebViewClient()
                    loadUrl("https://sentence.yourdictionary.com/$word")
                }
            })
    }
}



