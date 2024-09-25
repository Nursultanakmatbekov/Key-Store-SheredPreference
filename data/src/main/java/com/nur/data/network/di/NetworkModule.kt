package com.nur.data.network.di

import android.content.Context
import com.nur.data.local.TokenManager
import com.nur.data.local.prefs.TokenPreferenceHelper
import com.nur.data.network.apiservice.AnimeApiService
import com.nur.data.network.apiservice.SignInApiService
import com.nur.data.network.di.interceptor.TokenInterceptor
import com.nur.data.network.repository.AnimeRepositoryImpl
import com.nur.data.network.repository.SingInRepositoryImpl
import com.nur.domain.repostories.AnimeRepository
import com.nur.domain.repostories.auth.SingInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideTokenPreferenceHelper(
        @ApplicationContext context: Context,
        tokenManager: TokenManager
    ): TokenPreferenceHelper {
        return TokenPreferenceHelper(context, tokenManager)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        tokenPreferenceHelper: TokenPreferenceHelper
    ): TokenInterceptor {
        return TokenInterceptor(tokenPreferenceHelper)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedRetrofit(tokenInterceptor: TokenInterceptor): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val clientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(tokenInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeApiService(retrofit: Retrofit): AnimeApiService {
        return retrofit.create(AnimeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(apiService: AnimeApiService): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSignInApiService(retrofit: Retrofit): SignInApiService {
        return retrofit.create(SignInApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignInRepository(apiService: SignInApiService): SingInRepository {
        return SingInRepositoryImpl(apiService)
    }
}
