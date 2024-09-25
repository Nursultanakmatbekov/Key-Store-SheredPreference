package com.nur.data.network.di.interceptor

import com.nur.data.local.prefs.TokenPreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// Интерсептор для добавления токена аутентификации в HTTP-запросы
class TokenInterceptor @Inject constructor(
    private val tokenPreferenceHelper: TokenPreferenceHelper // Внедрение зависимостей: TokenPreferenceHelper
) : Interceptor {

    // Метод перехвата запросов
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request() // Получение оригинального HTTP-запроса

        // Получение расшифрованного токена доступа
        val accessToken = tokenPreferenceHelper.getDecryptedRefreshToken()

        // Создание нового запроса с добавлением заголовка Authorization, если токен доступен
        val requestBuilder = originalRequest.newBuilder().apply {
            if (accessToken != null) {
                addHeader("Authorization", "Bearer $accessToken") // Добавление заголовка с токеном
            }
        }

        // Выполнение запроса с модифицированными заголовками
        return chain.proceed(requestBuilder.build())
    }
}
