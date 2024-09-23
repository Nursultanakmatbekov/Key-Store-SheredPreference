package com.nur.data.network.dtos

import com.google.gson.annotations.SerializedName
import com.nur.domain.models.anime.Titles

data class TitlesDto(
    @SerializedName("ja_jp")
    val jaJp: String = "",
    @SerializedName("en_jp")
    val enJp: String = ""
)

fun TitlesDto.toDomain(): Titles = Titles(
    jaJp, enJp
)