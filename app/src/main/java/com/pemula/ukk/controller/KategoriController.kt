package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.Kategori
import com.pemula.ukk.model.KategoriRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KategoriController {

    suspend fun fetchKategoriList(): List<Kategori> {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.kategoriService.getKategori()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun fetchKategoriById(id: Long): Kategori? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.kategoriService.getKategoriById(id)
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

    suspend fun createKategori(request: KategoriRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.kategoriService.postKategori(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun updateKategori(id: Long, request: KategoriRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.kategoriService.putKategori(id, request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deleteKategori(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.kategoriService.deleteKategori(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
