package com.nur.data.network.dtos

import com.google.gson.annotations.SerializedName
import com.nur.domain.models.anime.AnimeResponce

data class AnimeResponceDto(
    @SerializedName("data")
    val data: List<AnimeDto>,
)

fun AnimeResponceDto.toDomain() = AnimeResponce(
    data = data.map {
        it.toDomain()
    }
)