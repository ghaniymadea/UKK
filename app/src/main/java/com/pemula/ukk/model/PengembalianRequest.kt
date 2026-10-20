package com.pemula.ukk.model

data class PengembalianRequest(
    val idPeminjaman: Long,
    val tanggalKembali: String?,
    val catatan: String?,
    val units: List<PengembalianUnitRequest>
)

data class PengembalianUnitRequest(
    val idUnitAlat: Long,
    val statusKondisi: String,
    val catatan: String?
)
