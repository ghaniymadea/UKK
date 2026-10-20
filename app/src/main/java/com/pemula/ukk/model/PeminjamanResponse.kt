package com.pemula.ukk.model

data class PeminjamanResponse(
    val id: Long,
    val idPeminjam: Long,
    val peminjam: String?,
    val idPetugas: Long?,
    val petugas: String?,
    val idStatusPeminjaman: Long? = null,
    val status: String,
    val tanggalPeminjaman: String,
    val tanggalPengembalian: String,
    val totalBiaya: Double,
    val createdAt: String,
    val details: List<PeminjamanDetailResponse> = emptyList()
)

data class PeminjamanDetailResponse(
    val id: Long,
    val idAlat: Long,
    val alat: String?,
    val jumlah: Int,
    val hargaSewaSaatTransaksi: Double,
    val idUnitAlat: List<Long> = emptyList(),
    val kodeInventaris: List<String> = emptyList()
)
