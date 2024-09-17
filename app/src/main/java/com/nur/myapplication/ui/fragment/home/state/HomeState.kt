package com.nur.myapplication.ui.fragment.home.state

import com.nur.myapplication.models.AnimeUI

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val anime: List<AnimeUI>) : HomeState()
    data class Error(val error: String) : HomeState()
}