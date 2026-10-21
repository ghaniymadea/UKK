package com.pemula.ukk.controller

import android.util.Log
import com.pemula.ukk.Services.Client
import com.pemula.ukk.model.LoginRequest
import com.pemula.ukk.model.LoginResponse
import com.pemula.ukk.model.UserResponse
import com.pemula.ukk.utils.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthController {

    suspend fun login(noTelepon: String, password: String): Pair<LoginResponse?, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(noTelepon = noTelepon, password = password)
                val response = Client.retrofitService.login(request)
                Log.d(
                    "LOGIN_DEBUG",
                    "Kode HTTP: ${response.code()}, body: ${response.body()}"
                )
                if (response.isSuccessful) {
                    Pair(response.body(), null)
                } else {
                    val error = ErrorUtils.parseError(response)
                    Log.e("LOGIN_DEBUG", "Login gagal (${response.code()}): title=${error.title}, detail=${error.detail}")
                    Pair(null, error.detail ?: error.title ?: "Nomor telepon atau password salah")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(null, e.localizedMessage ?: "Terjadi kesalahan koneksi")
            }
        }
    }

    suspend fun getProfile(): UserResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Client.retrofitService.getProfile()
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
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
