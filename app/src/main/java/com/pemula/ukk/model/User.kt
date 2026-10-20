package com.pemula.ukk.model

data class User(
    val id: Long,
    val idRole: Long,
    val idJenisKelamin: Long?,
    val nama: String,
    val noTelepon: String,
    val alamat: String?,
    val createdAt: String
)
