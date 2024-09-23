package com.nur.myapplication.models

import com.nur.domain.models.anime.Anime
import com.nur.domain.models.anime.PosterImage
import com.nur.domain.models.anime.Titles


data class AnimeUI(
    val attributes: AttributesUI,
    val id: String = "",
    val type: String = "",
)

fun Anime.toUI(): AnimeUI = AnimeUI(
    attributes = attributes.toUI(),
    id = id,
    type = type
)