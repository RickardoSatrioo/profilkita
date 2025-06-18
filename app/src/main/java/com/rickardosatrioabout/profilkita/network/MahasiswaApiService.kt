package com.rickardosatrioabout.profilkita.network

import com.rickardosatrioabout.profilkita.model.Mahasiswa
import com.rickardosatrioabout.profilkita.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://api-mobpro-v2.kakashispiritnews.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface OrangApiService {
    @GET("mahasiswa")
    suspend fun getMahasiswa(): List<Mahasiswa>

    @Multipart
    @POST("mahasiswa")
    suspend fun postMahasiswa(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("kelas") kelas: RequestBody,
        @Part("suku") suku : RequestBody,
        @Part image: MultipartBody.Part
    ): Mahasiswa
}

object MahasiswaApi {
    val service: OrangApiService by lazy {
        retrofit.create(OrangApiService::class.java)
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED}

    fun getMahasiswaUrl(gambar: String): String{
        return BASE_URL + gambar.removePrefix("/")
    }
}