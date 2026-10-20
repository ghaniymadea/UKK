package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PengembalianController {

    suspend fun fetchPengembalianList(
        page: Int? = null,
        pageSize: Int? = null,
        from: String? = null,
        to: String? = null
    ): PagedResult<PengembalianResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.pengembalianService.getPengembalian(page, pageSize, from, to)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchPengembalianById(id: Long): PengembalianResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.pengembalianService.getPengembalianById(id)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun createPengembalian(request: PengembalianRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.pengembalianService.postPengembalian(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
