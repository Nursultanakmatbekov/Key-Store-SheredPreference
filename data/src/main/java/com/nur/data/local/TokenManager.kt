package com.nur.data.local

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class TokenManager(private val context: Context) {

    // Хранилище ключей для шифрования
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "token_key" // Псевдоним для ключа

    init {
        createKey() // Создание ключа при инициализации класса
    }

    // Метод для создания ключа, если его еще нет в хранилище
    private fun createKey() {
        // Проверка, существует ли ключ с данным псевдонимом
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT // Цели ключа: шифрование и расшифрование
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM) // Режим блока: GCM
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE) // Отсутствие паддинга
                    .build()
            )
            keyGenerator.generateKey() // Генерация ключа
        }
    }

    // Метод для шифрования токена
    fun encryptToken(token: String): Pair<ByteArray, ByteArray> {
        val secretKey =
            keyStore.getKey(keyAlias, null) as SecretKey // Получение секретного ключа из хранилища
        val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(Cipher.ENCRYPT_MODE, secretKey) // Инициализация шифратора в режиме шифрования
        }
        val iv = cipher.iv // Получение вектора инициализации
        val encryptedToken = cipher.doFinal(token.toByteArray()) // Шифрование токена
        return Pair(encryptedToken, iv) // Возвращение зашифрованного токена и вектора инициализации
    }

    // Метод для расшифрования токена
    fun decryptToken(encryptedToken: ByteArray, iv: ByteArray): String {
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey // Получение секретного ключа
        val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(
                Cipher.DECRYPT_MODE,
                secretKey,
                GCMParameterSpec(128, iv)
            ) // Инициализация шифратора в режиме расшифрования
        }
        return String(cipher.doFinal(encryptedToken)) // Возвращение расшифрованного токена
    }
}
