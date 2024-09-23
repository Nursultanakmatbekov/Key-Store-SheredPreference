package com.nur.myapplication.models

import com.nur.domain.models.anime.PosterImage


data class PosterImageUI(
    val small: String = "",
    val original: String = "",
    val large: String = "",
    val tiny: String = "",
    val medium: String = ""
)

fun PosterImage.toUI(): PosterImageUI = PosterImageUI(
    small, original, large, tiny, medium
)