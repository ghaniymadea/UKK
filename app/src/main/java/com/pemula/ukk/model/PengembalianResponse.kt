package com.pemula.ukk.model

data class PengembalianResponse(
    val id: Long,
    val idPeminjaman: Long,
    val peminjam: String?,
    val tanggalKembali: String,
    val keterlambatanPengembalian: Int,
    val denda: Double,
    val catatan: String?,
    val details: List<PengembalianUnitResponse> = emptyList()
)

data class PengembalianUnitResponse(
    val idUnitAlat: Long,
    val kodeInventaris: String?,
    val alat: String?,
    val statusKondisi: String?,
    val dendaKondisi: Double,
    val catatan: String?
)
