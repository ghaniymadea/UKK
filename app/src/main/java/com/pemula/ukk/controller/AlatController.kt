package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.AlatRequest
import com.pemula.ukk.model.AlatResponse
import com.pemula.ukk.model.PagedResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlatController {

    suspend fun fetchAlatList(page: Int? = null, pageSize: Int? = null, search: String? = null, idKategori: Long? = null): PagedResult<AlatResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.alatService.getAlat(page = page, pageSize = pageSize, search = search, idKategori = idKategori)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchAlatById(id: Long): AlatResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.alatService.getAlatid(id)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun createAlat(request: AlatRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.alatService.postAlat(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun updateAlat(id: Long, request: AlatRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.alatService.putAlat(id, request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deleteAlat(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.alatService.deleteAlat(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
