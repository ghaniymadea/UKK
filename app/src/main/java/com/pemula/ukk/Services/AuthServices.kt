package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface AuthServices {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getProfile(): Response<UserResponse>

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String? = null
    ): Response<Any>
}
