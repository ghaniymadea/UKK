package com.pemula.ukk.model

data class LogAktivitas(
    val id: Long,
    val idUser: Long,
    val namaUser: String?,
    val aktivitas: String,
    val waktu: String?
)
