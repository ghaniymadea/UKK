package com.pemula.ukk.model

data class LoginResponse(
    val token: String,
    val expiresAt: String,
    val user: LoginUser
)

data class LoginUser(
    val id: Long,
    val nama: String,
    val role: String
)
