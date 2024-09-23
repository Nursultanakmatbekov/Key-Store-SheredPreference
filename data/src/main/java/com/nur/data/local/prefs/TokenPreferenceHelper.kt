package com.nur.data.local.prefs

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class TokenPreferenceHelper(context: Context) {

    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val prefs = EncryptedSharedPreferences.create(
        "token_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var accessToken: String?
        get() = prefs.getString("PREF_ACCESS_TOKEN", null)
        set(value) = prefs.edit().putString("PREF_ACCESS_TOKEN", value).apply()

    var refreshToken: String?
        get() = prefs.getString("PREF_REFRESH_TOKEN", null)
        set(value) = prefs.edit().putString("PREF_REFRESH_TOKEN", value).apply()

    var authIsShown: Boolean
        get() = prefs.getBoolean("AUTH_IS_SHOWN", false)
        set(value) = prefs.edit().putBoolean("AUTH_IS_SHOWN", value).apply()
}
