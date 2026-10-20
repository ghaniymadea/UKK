package com.pemula.ukk.model

data class Alat(
    val id: Long,
    val idKategori: Long,
    val nama: String,
    val deskripsi: String?,
    val hargaSewa: Double,
    val imagePath: String?,
    val createdAt: String?
)
