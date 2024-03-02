package com.example.words.presentation.word


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.words.R
import com.example.words.domain.entity.TopBarState
import com.example.words.navigation.NavigationState
import com.example.words.presentation.components.SingleTapButton
import com.example.words.utils.extentions.clickableOnce


@Composable
fun WordScreen(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    onComposingTopBar: (TopBarState) -> Unit
) {
    val viewModel: WordViewModel = hiltViewModel()
    val title = stringResource(R.string.word_screen_title)
    LaunchedEffect(key1 = true) {
        onComposingTopBar(
            TopBarState(
                topBarTitle = title,
                navigationIcon = null
            )
        )


    }
    Box(modifier = Modifier.padding(paddingValues)) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 16.dp,
                        end = 8.dp
                    )
            ) {
                SingleTapButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.updateWord()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),

                    ) {
                    Text(text = "Нe знаю")
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 8.dp,
                        end = 16.dp
                    )
            ) {
                SingleTapButton(
                    modifier = Modifier
                        .fillMaxWidth(),

                    onClick = {
                        viewModel.wordData.value?.let {
                            it.isStudied = true
                            viewModel.updateWordInDb(it)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.teal_700))
                ) {
                    Text(text = "Знаю")
                }
            }
        }
    }
    ObserveViewModel(navigationState, viewModel)
    viewModel.getWord()
}

@Composable
fun ObserveViewModel(
    navigationState: NavigationState,
    viewModel: WordViewModel
) {
    val wordData = viewModel.wordData.observeAsState()
    val countData = viewModel.countData.observeAsState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    wordData.value?.let { word ->
        clipboardManager.setText(AnnotatedString((word.word)))
        Text(
            modifier = Modifier
                .padding(top = 150.dp, start = 24.dp, end = 24.dp)
                /* .pointerInput(Unit){
                     detectTapGestures(
                         onDoubleTap  = {
                             clipboardManager.setText(AnnotatedString((word.word)))
                             Toast.makeText(context,
                                 "Copied to clipboard",
                                 Toast.LENGTH_LONG).show()
                         },

                         onTap  = {
                             navigationState.navigateToWebview(word.word)
                         },

                     )
                 }*/

                .clickableOnce {
                    navigationState.navigateToWebview(word.word)
                }
                .fillMaxWidth(),


            text = word.word,
            color = colorResource(R.color.black),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )

    }

    countData.value?.let { countStr ->
        val text = Text(
            modifier = Modifier
                .padding(24.dp),
            text = countStr,
            color = colorResource(R.color.black),
            fontSize = 12.sp,
            textAlign = TextAlign.Start

        )
    }
}




