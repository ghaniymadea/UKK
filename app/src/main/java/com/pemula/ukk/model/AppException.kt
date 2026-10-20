package com.pemula.ukk.model

data class AppException(
    val statusCode: Int,
    val detail: String?,
    val title: String? = null
) : Exception(detail ?: "Error $statusCode")
