package com.pemula.ukk.model

data class ReportPengembalian(
    val id: Long,
    val idPeminjaman: Long,
    val peminjam: String?,
    val tanggalKembali: String,
    val keterlambatanPengembalian: Int,
    val denda: Double,
    val dendaKondisi: Double
)
