package com.nur.myapplication.models

import com.nur.domain.models.anime.Titles


data class TitlesUI(
    val jaJp: String = "",
    val enJp: String = ""
)

fun Titles.toUI(): TitlesUI = TitlesUI(
    jaJp, enJp
)