package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.StatusBarang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatusBarangController {

    suspend fun fetchStatusBarangList(): List<StatusBarang> {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.statusBarangService.getStatusBarang()
                if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun createStatusBarang(statusBarang: StatusBarang): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.statusBarangService.postStatusBarang(statusBarang)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun patchStatusBarang(id: Long, statusBarang: StatusBarang): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.statusBarangService.patchStatusBarang(id, statusBarang)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun deleteStatusBarang(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.statusBarangService.deleteStatusBarang(id)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
