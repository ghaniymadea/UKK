package com.pemula.ukk.model

data class MonitorPeminjaman(
    val id: Long,
    val peminjam: String?,
    val noTelepon: String?,
    val tanggalPeminjaman: String?,
    val tanggalPengembalian: String?,
    val hariTerlambat: Int,
    val totalBiaya: Double
)
