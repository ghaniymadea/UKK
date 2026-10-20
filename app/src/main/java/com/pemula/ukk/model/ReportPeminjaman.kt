package com.pemula.ukk.model

data class ReportPeminjaman(
    val id: Long,
    val peminjam: String?,
    val petugas: String?,
    val status: String?,
    val tanggalPeminjaman: String,
    val tanggalPengembalian: String,
    val totalBiaya: Double,
    val jumlahAlat: Int
)
