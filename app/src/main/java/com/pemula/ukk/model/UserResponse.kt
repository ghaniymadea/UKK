package com.pemula.ukk.model

data class UserResponse(
    val id: Long,
    val nama: String,
    val noTelepon: String?,
    val alamat: String?,
    val idRole: Long,
    val role: String?,
    val idJenisKelamin: Long?,
    val jenisKelamin: String?,
    val createdAt: String?
)
