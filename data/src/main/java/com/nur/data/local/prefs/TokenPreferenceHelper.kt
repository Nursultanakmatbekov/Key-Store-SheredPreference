package com.nur.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.nur.data.local.TokenManager
import javax.inject.Inject

class TokenPreferenceHelper @Inject constructor(
    context: Context,
    private val tokenManager: TokenManager
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Получение доступа к токену из SharedPreferences
    var accessToken: String?
        get() = prefs.getString("access_token", null) // Чтение access_token
        set(value) = prefs.edit().putString("access_token", value).apply() // Сохранение access_token

    // Получение обновляющего токена из SharedPreferences
    var refreshToken: String?
        get() = prefs.getString("refresh_token", null) // Чтение refresh_token
        set(value) = prefs.edit().putString("refresh_token", value).apply() // Сохранение refresh_token

    // Метод для сохранения зашифрованных токенов и векторов инициализации в SharedPreferences
    fun saveTokens(
        encryptedAccessToken: ByteArray, // Зашифрованный access_token
        ivAccess: ByteArray, // Вектор инициализации для access_token
        encryptedRefreshToken: ByteArray, // Зашифрованный refresh_token
        ivRefresh: ByteArray // Вектор инициализации для refresh_token
    ) {
        val accessTokenString = Base64.encodeToString(encryptedAccessToken, Base64.DEFAULT) // Кодирование access_token в строку
        val ivAccessString = Base64.encodeToString(ivAccess, Base64.DEFAULT) // Кодирование вектора инициализации для access_token в строку
        val refreshTokenString = Base64.encodeToString(encryptedRefreshToken, Base64.DEFAULT) // Кодирование refresh_token в строку
        val ivRefreshString = Base64.encodeToString(ivRefresh, Base64.DEFAULT) // Кодирование вектора инициализации для refresh_token в строку

        // Сохранение всех токенов и векторов инициализации в SharedPreferences
        prefs.edit()
            .putString("access_token", accessTokenString)
            .putString("iv_access", ivAccessString)
            .putString("refresh_token", refreshTokenString)
            .putString("iv_refresh", ivRefreshString)
            .apply()
    }

    // Метод для получения расшифрованного обновляющего токена
    fun getDecryptedRefreshToken(): String? {
        val encryptedRefreshTokenString = prefs.getString("refresh_token", null) // Получение зашифрованного refresh_token
        val ivRefreshString = prefs.getString("iv_refresh", null) // Получение вектора инициализации для refresh_token

        // Проверка наличия зашифрованного токена и вектора инициализации
        if (encryptedRefreshTokenString != null && ivRefreshString != null) {
            // Декодирование строки в байты
            val encryptedRefreshToken = Base64.decode(encryptedRefreshTokenString, Base64.DEFAULT)
            val ivRefresh = Base64.decode(ivRefreshString, Base64.DEFAULT)

            return try {
                // Попытка расшифровать refresh_token с использованием TokenManager
                tokenManager.decryptToken(encryptedRefreshToken, ivRefresh)
            } catch (e: Exception) {
                // Логирование или обработка ошибки, если расшифровка не удалась
                e.printStackTrace()
                null // Возвращение null в случае ошибки
            }
        }
        return null // Возвращение null, если токен или вектор не найдены
    }

    // Переменная для отслеживания, было ли показано окно аутентификации
    var authIsShown: Boolean
        get() = prefs.getBoolean("auth_is_shown", false) // Получение значения из SharedPreferences
        set(value) = prefs.edit().putBoolean("auth_is_shown", value).apply() // Сохранение значения в SharedPreferences
}
