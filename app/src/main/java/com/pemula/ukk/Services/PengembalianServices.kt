package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface PengembalianServices {
    @GET("pengembalian")
    suspend fun getPengembalian(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null
    ): Response<PagedResult<PengembalianResponse>>

    @GET("pengembalian/{id}")
    suspend fun getPengembalianById(
        @Path("id") id: Long
    ): Response<PengembalianResponse>

    @POST("pengembalian")
    suspend fun postPengembalian(
        @Body request: PengembalianRequest
    ): Response<Unit>
}
