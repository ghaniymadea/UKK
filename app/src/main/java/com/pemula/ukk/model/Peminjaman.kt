package com.pemula.ukk.model

data class Peminjaman(
    val id: Long,
    val idPeminjam: Long,
    val idPetugas: Long?,
    val idStatusPeminjaman: Long,
    val status: String?,
    val tanggalPeminjaman: String,
    val tanggalPengembalian: String,
    val totalBiaya: Double,
    val createdAt: String
)
