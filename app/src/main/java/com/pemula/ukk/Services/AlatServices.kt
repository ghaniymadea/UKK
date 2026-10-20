package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface AlatServices {
    @GET("alat")
    suspend fun getAlat(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("idKategori") idKategori: Long? = null
    ): Response<PagedResult<AlatResponse>>

    @POST("alat")
    suspend fun postAlat(
        @Body request: AlatRequest
    ): Response<Unit>

    @GET("alat/{id}")
    suspend fun getAlatid(
        @Path("id") id: Long
    ): Response<AlatResponse>

    @PUT("alat/{id}")
    suspend fun putAlat(
        @Path("id") id: Long,
        @Body request: AlatRequest
    ): Response<Unit>

    @DELETE("alat/{id}")
    suspend fun deleteAlat(
        @Path("id") id: Long
    ): Response<Unit>
}
