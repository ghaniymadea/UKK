package com.pemula.ukk.Services

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {
    var BASE_URL = "http://192.168.69.95:3000/api/v1/"
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun getHostUrl(): String {
        val clean = BASE_URL.removeSuffix("/")
        return if (clean.endsWith("/api/v1")) {
            clean.removeSuffix("/api/v1")
        } else {
            clean
        }
    }

    fun getImageUrl(imagePath: String?): String? {
        if (imagePath.isNullOrEmpty()) return null
        if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) return imagePath
        val host = getHostUrl()
        val cleanPath = if (imagePath.startsWith("/")) imagePath else "/$imagePath"
        return "$host$cleanPath"
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
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun buildRetrofit(): Retrofit {
        val validUrl = if (BASE_URL.endsWith("/")) BASE_URL else "$BASE_URL/"
        return Retrofit.Builder()
            .baseUrl(validUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService: AuthServices get() = buildRetrofit().create(AuthServices::class.java)
    val alatService: AlatServices get() = buildRetrofit().create(AlatServices::class.java)
    val kategoriService: KategoriServices get() = buildRetrofit().create(KategoriServices::class.java)
    val peminjamanService: PeminjamanServices get() = buildRetrofit().create(PeminjamanServices::class.java)
    val pengembalianService: PengembalianServices get() = buildRetrofit().create(PengembalianServices::class.java)
    val unitAlatService: UnitAlatServices get() = buildRetrofit().create(UnitAlatServices::class.java)
    val usersService: UsersServices get() = buildRetrofit().create(UsersServices::class.java)
    val laporanService: LaporanServices get() = buildRetrofit().create(LaporanServices::class.java)
    val logAktivitasService: LogAktivitasServices get() = buildRetrofit().create(LogAktivitasServices::class.java)
    val statusBarangService: StatusBarangServices get() = buildRetrofit().create(StatusBarangServices::class.java)
}
