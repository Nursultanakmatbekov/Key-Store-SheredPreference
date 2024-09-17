package com.nur.data.network.apiservice

import com.nur.data.network.dtos.AnimeResponceDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {
    @GET("edge/anime")
    suspend fun getAnime(): AnimeResponceDto
}