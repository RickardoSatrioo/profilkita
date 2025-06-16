package com.rickardosatrioabout.profilkita.network

import com.rickardosatrioabout.profilkita.model.Mahasiswa
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api-mobpro.kakashispiritnews.my.id/"

private val moshi = Moshi.Builder()
    . add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OrangApiService {
    @GET("/")
    suspend fun getMahasiswa(): List<Mahasiswa>
}

object MahasiswaApi {
    val service: OrangApiService by lazy {
        retrofit.create(OrangApiService::class.java)
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED}

    fun getMahasiswaUrl(gambar: String): String{
        return gambar
    }
}