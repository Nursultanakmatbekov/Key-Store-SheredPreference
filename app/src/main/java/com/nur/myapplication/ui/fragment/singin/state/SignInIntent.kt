package com.nur.myapplication.ui.fragment.singin.state

sealed class SignInIntent {
    data class Submit(val email: String, val password: String) : SignInIntent()
}