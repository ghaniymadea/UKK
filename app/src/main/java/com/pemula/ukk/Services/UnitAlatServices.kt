package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface UnitAlatServices {
    @GET("unit-alat")
    suspend fun getUnitAlat(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("idAlat") idAlat: Long? = null,
        @Query("idStatusKetersediaan") idStatusKetersediaan: Long? = null
    ): Response<PagedResult<UnitAlat>>

    @GET("unit-alat/{id}")
    suspend fun getUnitAlatById(
        @Path("id") id: Long
    ): Response<UnitAlat>

    @POST("unit-alat")
    suspend fun postUnitAlat(
        @Body request: UnitAlatRequest
    ): Response<Unit>

    @PUT("unit-alat/{id}")
    suspend fun putUnitAlat(
        @Path("id") id: Long,
        @Body request: UnitAlatUpdateRequest
    ): Response<Unit>

    @DELETE("unit-alat/{id}")
    suspend fun deleteUnitAlat(
        @Path("id") id: Long
    ): Response<Unit>
}
