package com.pemula.ukk.model

data class PagedResult<T>(
    val items: List<T>,
    val page: Int,
    val pageSize: Int,
    val total: Long
)
