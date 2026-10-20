package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeminjamanController {

    suspend fun fetchPeminjamanList(
        page: Int? = null,
        pageSize: Int? = null,
        status: String? = null,
        idPeminjam: Long? = null,
        from: String? = null,
        to: String? = null
    ): PagedResult<PeminjamanResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.getPeminjaman(page, pageSize, status, idPeminjam, from, to)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchMyPeminjaman(page: Int? = null, pageSize: Int? = null, status: String? = null): PagedResult<PeminjamanResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.getMyPeminjaman(page, pageSize, status)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchMonitor(page: Int? = null, pageSize: Int? = null, status: String? = null): PagedResult<MonitorPeminjaman>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.getPeminjamanMonitor(page, pageSize, status)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchPeminjamanById(id: Long): PeminjamanResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.getPeminjamanById(id)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun createPeminjaman(request: PeminjamanRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.postPeminjaman(request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun updatePeminjaman(id: Long, request: PeminjamanRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.putPeminjaman(id, request)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deletePeminjaman(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.deletePeminjaman(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun approvePeminjaman(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.approvePeminjaman(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun rejectPeminjaman(id: Long, catatan: String?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.rejectPeminjaman(id, RejectRequest(catatan))
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun borrowPeminjaman(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.peminjamanService.borrowPeminjaman(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
