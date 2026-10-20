package com.pemula.ukk.controller

import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.LoginRequest
import com.pemula.ukk.model.LoginResponse
import com.pemula.ukk.utils.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthController {

    suspend fun login(noTelepon: String, password: String): Pair<LoginResponse?, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(noTelepon = noTelepon, password = password)
                val response = Client.retrofitService.login(request)
                if (response.isSuccessful) {
                    Pair(response.body(), null)
                } else {
                    val error = ErrorUtils.parseError(response)
                    Pair(null, error.detail ?: "Login gagal")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(null, e.localizedMessage ?: "Terjadi kesalahan koneksi")
            }
        }
    }

    suspend fun logout(token: String? = null): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val authHeader = if (token != null) "Bearer $token" else null
                val response = Client.retrofitService.logout(authHeader)
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
