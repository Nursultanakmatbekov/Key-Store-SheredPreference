package com.nur.data.network.dtos

import com.google.gson.annotations.SerializedName
import com.nur.domain.models.anime.PosterImage

data class PosterImageDto(
    @SerializedName("small")
    val small: String = "",
    @SerializedName("original")
    val original: String = "",
    @SerializedName("large")
    val large: String = "",
    @SerializedName("tiny")
    val tiny: String = "",
    @SerializedName("medium")
    val medium: String = ""
)

fun PosterImageDto.toDomain(): PosterImage = PosterImage(
    small, original, large, tiny, medium
)