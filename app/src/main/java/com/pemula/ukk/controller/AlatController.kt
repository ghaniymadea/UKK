package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.AlatRequest
import com.pemula.ukk.model.AlatResponse
import com.pemula.ukk.model.PagedResult
import com.pemula.ukk.utils.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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

    suspend fun createAlat(
        idKategori: Long,
        nama: String,
        deskripsi: String?,
        hargaSewa: Double,
        gambarPart: MultipartBody.Part? = null
    ): Pair<Boolean, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val kategoriBody = idKategori.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val namaBody = nama.toRequestBody("text/plain".toMediaTypeOrNull())
                val deskripsiBody = (deskripsi ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
                val hargaSewaBody = hargaSewa.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val response = Client.alatService.postAlat(
                    kategoriBody, namaBody, deskripsiBody, hargaSewaBody, gambarPart
                )
                if (response.isSuccessful) {
                    Pair(true, null)
                } else {
                    val err = ErrorUtils.parseError(response)
                    Pair(false, err.detail)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(false, e.localizedMessage ?: "Terjadi kesalahan jaringan/server")
            }
        }
    }

    suspend fun createAlat(request: AlatRequest): Boolean {
        val (success, _) = createAlat(
            idKategori = request.idKategori,
            nama = request.nama,
            deskripsi = request.deskripsi,
            hargaSewa = request.hargaSewa,
            gambarPart = null
        )
        return success
    }

    suspend fun updateAlat(
        id: Long,
        idKategori: Long,
        nama: String,
        deskripsi: String?,
        hargaSewa: Double,
        gambarPart: MultipartBody.Part? = null
    ): Pair<Boolean, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val kategoriBody = idKategori.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val namaBody = nama.toRequestBody("text/plain".toMediaTypeOrNull())
                val deskripsiBody = (deskripsi ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
                val hargaSewaBody = hargaSewa.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val response = Client.alatService.putAlat(
                    id, kategoriBody, namaBody, deskripsiBody, hargaSewaBody, gambarPart
                )
                if (response.isSuccessful) {
                    Pair(true, null)
                } else {
                    val err = ErrorUtils.parseError(response)
                    Pair(false, err.detail)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(false, e.localizedMessage ?: "Terjadi kesalahan jaringan/server")
            }
        }
    }

    suspend fun updateAlat(id: Long, request: AlatRequest): Boolean {
        val (success, _) = updateAlat(
            id = id,
            idKategori = request.idKategori,
            nama = request.nama,
            deskripsi = request.deskripsi,
            hargaSewa = request.hargaSewa,
            gambarPart = null
        )
        return success
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
