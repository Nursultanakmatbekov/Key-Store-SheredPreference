package com.nur.data.network.dtos

import com.google.gson.annotations.SerializedName
import com.nur.domain.models.anime.Anime

data class AnimeDto(
    @SerializedName("attributes")
    val attributes: AttributesDto,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("type")
    val type: String = ""
)

fun AnimeDto.toDomain(): Anime = Anime(
    attributes.toDomain(),
    id, type
)