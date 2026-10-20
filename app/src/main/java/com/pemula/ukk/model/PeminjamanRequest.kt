package com.pemula.ukk.model

data class PeminjamanRequest(
    val tanggalPeminjaman: String?,
    val tanggalPengembalian: String?,
    val items: List<PeminjamanItemRequest>
)

data class PeminjamanItemRequest(
    val idAlat: Long,
    val jumlah: Int
)
