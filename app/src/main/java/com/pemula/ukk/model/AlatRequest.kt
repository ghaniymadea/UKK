package com.pemula.ukk.model

data class AlatRequest(
    val idKategori: Long,
    val nama: String,
    val deskripsi: String?,
    val hargaSewa: Double,
    val imagePath: String?
)
