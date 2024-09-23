package com.nur.myapplication.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nur.domain.usecase.AnimeUseCase
import com.nur.myapplication.models.toUI
import com.nur.myapplication.ui.fragment.home.state.HomeIntent
import com.nur.myapplication.ui.fragment.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> = _state

    private val _intent = Channel<HomeIntent>(Channel.UNLIMITED)
    val intent = _intent.receiveAsFlow()

    fun send(intent: HomeIntent) {
        viewModelScope.launch {
            _intent.send(intent)
        }
    }

    init {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is HomeIntent.LoadAnime -> fetchAnime()
                }
            }
        }
    }

    private fun fetchAnime() {
        viewModelScope.launch(Dispatchers.IO) {
                animeUseCase.invoke().collect { animeList ->
                    _state.value = HomeState.Success(
                        animeList.map { it.toUI() })
                }
        }
    }
}