package com.nur.myapplication.models

import com.nur.domain.models.anime.Attributes


data class AttributesUI(
    val description: String = "",
    val status: String = ""
)

fun Attributes.toUI(): AttributesUI = AttributesUI(
    description,
    status
)