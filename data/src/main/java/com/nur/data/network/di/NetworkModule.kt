package com.nur.data.network.di

import com.nur.data.network.apiservice.AnimeApiService
import com.nur.data.network.repository.AnimeRepositoryImpl
import com.nur.domain.repostories.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AnimeApiService {
        return retrofit.create(AnimeApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideAnimeRepository(apiService: AnimeApiService): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }
}