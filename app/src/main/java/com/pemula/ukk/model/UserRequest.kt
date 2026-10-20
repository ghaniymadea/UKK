package com.pemula.ukk.model

data class UserRequest(
    val idRole: Long,
    val idJenisKelamin: Long?,
    val nama: String,
    val password: String?,
    val noTelepon: String,
    val alamat: String?
)
