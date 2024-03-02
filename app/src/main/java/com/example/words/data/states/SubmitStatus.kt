package com.example.words.data.states

sealed class SubmitStatus {
    object ReadyToSubmit : SubmitStatus()
    object InProgress : SubmitStatus()
    object Success : SubmitStatus()
    data class Error(val message: String) : SubmitStatus()
}