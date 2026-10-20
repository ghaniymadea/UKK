package com.pemula.ukk.model

data class AlatResponse(
    val id: Long,
    val idKategori: Long,
    val kategori: String?,
    val nama: String,
    val deskripsi: String?,
    val hargaSewa: Double,
    val imagePath: String?,
    val stokTersedia: Int,
    val createdAt: String?
)
