package com.nur.domain.usecase

import com.nur.domain.repostories.AnimeRepository
import javax.inject.Inject

class AnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke() =
        repository.fetchAnime()

}