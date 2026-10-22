package com.pemula.ukk.Services

import com.pemula.ukk.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("alat")
    suspend fun postAlat(
        @Part("idKategori") idKategori: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody?,
        @Part("hargaSewa") hargaSewa: RequestBody,
        @Part gambar: MultipartBody.Part?
    ): Response<Unit>

    @GET("alat/{id}")
    suspend fun getAlatid(
        @Path("id") id: Long
    ): Response<AlatResponse>

    @Multipart
    @PUT("alat/{id}")
    suspend fun putAlat(
        @Path("id") id: Long,
        @Part("idKategori") idKategori: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody?,
        @Part("hargaSewa") hargaSewa: RequestBody,
        @Part gambar: MultipartBody.Part?
    ): Response<Unit>

    @DELETE("alat/{id}")
    suspend fun deleteAlat(
        @Path("id") id: Long
    ): Response<Unit>
}
