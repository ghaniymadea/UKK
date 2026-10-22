package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class LaporanController {

    suspend fun fetchLaporanPeminjaman(from: String? = null, to: String? = null, format: String? = null): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.laporanService.getLaporanPeminjaman(from = from, to = to, format = format)
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

    suspend fun fetchLaporanPengembalian(from: String? = null, to: String? = null, format: String? = null): Any? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.laporanService.getLaporanPengembalian(from = from, to = to, format = format)
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

    suspend fun downloadPeminjamanCsv(from: String? = null, to: String? = null): ResponseBody? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.laporanService.downloadLaporanPeminjamanCsv(from = from, to = to, format = "csv")
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
