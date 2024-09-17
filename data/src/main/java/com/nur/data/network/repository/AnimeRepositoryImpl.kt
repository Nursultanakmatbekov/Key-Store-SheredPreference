package com.nur.data.network.repository

import com.nur.data.network.apiservice.AnimeApiService
import com.nur.data.network.dtos.toDomain
import com.nur.domain.models.anime.Anime
import com.nur.domain.repostories.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: AnimeApiService
) : AnimeRepository {

    override fun fetchAnime(): Flow<List<Anime>> = flow {
        try {
            val response = apiService.getAnime()

            val animeList = response.data.map { it.toDomain() }

            emit(animeList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
