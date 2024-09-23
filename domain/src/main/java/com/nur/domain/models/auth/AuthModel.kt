package com.nur.domain.models.auth


data class AuthModel(
    val grant_type: String = "password",
    val email: String,
    val password: String
)
