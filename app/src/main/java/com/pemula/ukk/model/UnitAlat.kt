package com.pemula.ukk.model

data class UnitAlat(
    val id: Long,
    val idAlat: Long,
    val namaAlat: String?,
    val idStatusKetersediaan: Long,
    val statusKetersediaan: String?,
    val kodeInventaris: String,
    val idStatusBarang: Long,
    val statusBarang: String?,
    val createdAt: String?
)
