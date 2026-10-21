package com.pemula.ukk.model

data class LoginResponse(
    val token: String,
    val expiresAt: String,
    val user: UserResponse
)
