package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import retrofit2.Response
import retrofit2.http.*

interface KategoriServices {
    @GET("kategori")
    suspend fun getKategori(): Response<List<Kategori>>

    @GET("kategori/{id}")
    suspend fun getKategoriById(
        @Path("id") id: Long
    ): Response<Kategori>

    @POST("kategori")
    suspend fun postKategori(
        @Body request: KategoriRequest
    ): Response<Unit>

    @PUT("kategori/{id}")
    suspend fun putKategori(
        @Path("id") id: Long,
        @Body request: KategoriRequest
    ): Response<Unit>

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(
        @Path("id") id: Long
    ): Response<Unit>
}
