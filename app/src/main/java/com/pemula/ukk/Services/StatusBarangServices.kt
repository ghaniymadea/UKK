package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface StatusBarangServices {
    @GET("status-barang")
    suspend fun getStatusBarang(): Response<List<StatusBarang>>

    @POST("status-barang")
    suspend fun postStatusBarang(
        @Body statusBarang: StatusBarang
    ): Response<Unit>

    @PATCH("status-barang/{id}")
    suspend fun patchStatusBarang(
        @Path("id") id: Long,
        @Body statusBarang: StatusBarang
    ): Response<Unit>

    @DELETE("status-barang/{id}")
    suspend fun deleteStatusBarang(
        @Path("id") id: Long
    ): Response<Unit>
}
