package com.nur.domain.repostories

import com.nur.domain.models.anime.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun fetchAnime(): Flow<List<Anime>>

}