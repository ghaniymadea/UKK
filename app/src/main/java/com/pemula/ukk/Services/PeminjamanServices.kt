package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface PeminjamanServices {
    @GET("peminjaman")
    suspend fun getPeminjaman(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("status") status: String? = null,
        @Query("idPeminjam") idPeminjam: Long? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null
    ): Response<PagedResult<PeminjamanResponse>>

    @POST("peminjaman")
    suspend fun postPeminjaman(
        @Body request: PeminjamanRequest
    ): Response<Unit>

    @GET("peminjaman/mine")
    suspend fun getMyPeminjaman(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("status") status: String? = null
    ): Response<PagedResult<PeminjamanResponse>>

    @GET("peminjaman/monitor")
    suspend fun getPeminjamanMonitor(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("status") status: String? = null
    ): Response<PagedResult<MonitorPeminjaman>>

    @GET("peminjaman/{id}")
    suspend fun getPeminjamanById(
        @Path("id") id: Long
    ): Response<PeminjamanResponse>

    @PUT("peminjaman/{id}")
    suspend fun putPeminjaman(
        @Path("id") id: Long,
        @Body request: PeminjamanRequest
    ): Response<Unit>

    @DELETE("peminjaman/{id}")
    suspend fun deletePeminjaman(
        @Path("id") id: Long
    ): Response<Unit>

    @PATCH("peminjaman/{id}/approve")
    suspend fun approvePeminjaman(
        @Path("id") id: Long
    ): Response<Unit>

    @PATCH("peminjaman/{id}/reject")
    suspend fun rejectPeminjaman(
        @Path("id") id: Long,
        @Body request: RejectRequest
    ): Response<Unit>

    @PATCH("peminjaman/{id}/borrow")
    suspend fun borrowPeminjaman(
        @Path("id") id: Long
    ): Response<Unit>
}
