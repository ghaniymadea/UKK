package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface LogAktivitasServices {
    @GET("log-aktivitas")
    suspend fun getLogAkativitas(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("idUser") idUser: Long? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null
    ): Response<PagedResult<LogAktivitas>>
}
