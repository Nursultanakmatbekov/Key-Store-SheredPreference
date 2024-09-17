package com.nur.myapplication.models

import com.nur.domain.models.anime.AnimeResponce

data class AnimeResponceUI(
    val data: List<AnimeUI>,
)

fun AnimeResponce.toUI() = AnimeResponceUI(
    data = data.map {
        it.toUI()
    }
)