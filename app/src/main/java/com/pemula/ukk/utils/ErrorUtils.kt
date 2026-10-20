package com.pemula.ukk.utils

import com.google.gson.Gson
import com.pemula.ukk.model.AppException
import retrofit2.Response

object ErrorUtils {
    fun parseError(response: Response<*>): AppException {
        return try {
            val errorJson = response.errorBody()?.string()
            if (!errorJson.isNullOrEmpty()) {
                val gson = Gson()
                @Suppress("UNCHECKED_CAST")
                val errorObj = gson.fromJson(errorJson, Map::class.java) as? Map<String, Any>
                val status = (errorObj?.get("status") as? Double)?.toInt() ?: response.code()
                val detail = (errorObj?.get("detail") as? String) ?: response.message()
                val title = errorObj?.get("title") as? String
                AppException(status, detail, title)
            } else {
                AppException(response.code(), response.message())
            }
        } catch (e: Exception) {
            AppException(response.code(), response.message())
        }
    }
}
