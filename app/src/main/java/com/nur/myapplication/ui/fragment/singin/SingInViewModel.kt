package com.nur.myapplication.ui.fragment.singin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nur.domain.usecase.SingInUseCase
import com.nur.myapplication.models.auth.toUI
import com.nur.myapplication.ui.fragment.singin.state.SignInIntent
import com.nur.myapplication.ui.fragment.singin.state.SingInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SingInViewModel @Inject constructor(
    private val signInUseCase: SingInUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow<SingInState>(SingInState.Idle)
    val signInState = _signInState.asStateFlow()

    private val _intentChannel = Channel<SignInIntent>(Channel.UNLIMITED)
    val intents = _intentChannel.receiveAsFlow()

    init {
        processIntents()
    }

    fun send(intent: SignInIntent) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is SignInIntent.Submit -> login(intent.email, intent.password)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        _signInState.value = SingInState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                signInUseCase(email, password).collect { loginResponse ->
                    _signInState.value = SingInState.Success(loginResponse.map { it.toUI() })
                }
            } catch (e: Exception) {
                _signInState.value = SingInState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
