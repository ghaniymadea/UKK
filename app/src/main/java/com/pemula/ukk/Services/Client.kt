package com.pemula.ukk.Services

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {
    private const val BASE_URL = "http://192.168.69.58:3001/api/v1/"
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val path = original.url.encodedPath
        val token = appContext?.getSharedPreferences("CampGearPrefs", Context.MODE_PRIVATE)
            ?.getString("token", null)
        val requestBuilder = original.newBuilder()
        if (!token.isNullOrEmpty() && !path.contains("auth/login")) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: AuthServices by lazy {
        retrofit.create(AuthServices::class.java)
    }

    val alatService: AlatServices by lazy {
        retrofit.create(AlatServices::class.java)
    }

    val kategoriService: KategoriServices by lazy {
        retrofit.create(KategoriServices::class.java)
    }

    val peminjamanService: PeminjamanServices by lazy {
        retrofit.create(PeminjamanServices::class.java)
    }

    val pengembalianService: PengembalianServices by lazy {
        retrofit.create(PengembalianServices::class.java)
    }

    val unitAlatService: UnitAlatServices by lazy {
        retrofit.create(UnitAlatServices::class.java)
    }

    val usersService: UsersServices by lazy {
        retrofit.create(UsersServices::class.java)
    }

    val laporanService: LaporanServices by lazy {
        retrofit.create(LaporanServices::class.java)
    }

    val logAktivitasService: LogAktivitasServices by lazy {
        retrofit.create(LogAktivitasServices::class.java)
    }

    val statusBarangService: StatusBarangServices by lazy {
        retrofit.create(StatusBarangServices::class.java)
    }
}
