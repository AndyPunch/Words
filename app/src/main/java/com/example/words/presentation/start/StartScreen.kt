package com.example.words.presentation.start

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.words.data.states.SubmitStatus
import com.example.words.navigation.rememberNavigationState
import com.example.words.presentation.components.ProgressIndicator
import com.example.words.presentation.main.MainScreen


@Preview
@Composable
fun StartScreen() {
    val viewModel: StartViewModel = hiltViewModel()
    val navigationState = rememberNavigationState()
    viewModel.setNavigationState(navigationState)
    ObserveViewModel(viewModel)
    viewModel.getData()

}

@Composable
fun ObserveViewModel(viewModel: StartViewModel) {
    val submitStatus = viewModel.submitStatus.observeAsState()
    var isNextScreen = viewModel.isNextScreen.observeAsState()

    when (submitStatus.value) {
        SubmitStatus.ReadyToSubmit -> {
        }

        SubmitStatus.InProgress -> {
            ProgressIndicator()
        }

        is SubmitStatus.Error -> {
            val error = (submitStatus.value as SubmitStatus.Error).message
            Toast.makeText(LocalContext.current, error, Toast.LENGTH_LONG).show()
        }

        SubmitStatus.Success -> {

        }

        else -> {}
    }

    when (isNextScreen.value) {
        true -> {
            MainScreen()
        }

        else -> {}
    }

}
