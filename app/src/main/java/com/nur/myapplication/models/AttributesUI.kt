package com.nur.myapplication.models

import com.nur.domain.models.anime.Attributes


data class AttributesUI(
    val description: String = "",
    val status: String = "",
    val posterImage: PosterImageUI,
    val titles: TitlesUI,
)

fun Attributes.toUI(): AttributesUI = AttributesUI(
    description,
    status,
    posterImage.toUI(),
    titles.toUI(),
)