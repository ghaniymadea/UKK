package com.pemula.ukk.Services

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LaporanServices {
    @GET("laporan/peminjaman")
    suspend fun getLaporanPeminjaman(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("format") format: String? = null
    ): Response<Any>

    @GET("laporan/pengembalian")
    suspend fun getLaporanPengembalian(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("format") format: String? = null
    ): Response<Any>

    @GET("laporan/peminjaman")
    suspend fun downloadLaporanPeminjamanCsv(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("format") format: String = "csv"
    ): Response<ResponseBody>

    @GET("laporan/pengembalian")
    suspend fun downloadLaporanPengembalianCsv(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("format") format: String = "csv"
    ): Response<ResponseBody>
}
