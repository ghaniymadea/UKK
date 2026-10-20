package com.pemula.ukk.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("CampGearPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USER_NAME = "userName"
        private const val KEY_USER_ROLE = "userRole"
        private const val KEY_USER_PHONE = "userPhone"
        private const val KEY_USER_ALAMAT = "userAlamat"
    }

    fun saveLoginSession(token: String?, userId: Long, name: String?, role: String?, phone: String? = null, alamat: String? = null) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_ROLE, role)
            putString(KEY_USER_PHONE, phone)
            putString(KEY_USER_ALAMAT, alamat)
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)
    fun getUserId(): Long = prefs.getLong(KEY_USER_ID, 0L)
    fun getUserName(): String? = prefs.getString(KEY_USER_NAME, "name")
    fun getUserRole(): String? = prefs.getString(KEY_USER_ROLE, "")
    fun getUserPhone(): String? = prefs.getString(KEY_USER_PHONE, "nomor telp")
    fun getUserAlamat(): String? = prefs.getString(KEY_USER_ALAMAT, "alamat")

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
