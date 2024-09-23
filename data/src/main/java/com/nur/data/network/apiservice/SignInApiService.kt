package com.nur.data.network.apiservice

import com.nur.data.network.dtos.auth.AuthModelDto
import com.nur.data.network.dtos.auth.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApiService {

    @POST("oauth/token")
    suspend fun postAuthDataUser(
        @Body authModel: AuthModelDto
    ): LoginResponseDto
}
