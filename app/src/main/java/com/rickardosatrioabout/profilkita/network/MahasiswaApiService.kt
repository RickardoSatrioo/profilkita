package com.rickardosatrioabout.profilkita.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api-mobpro.kakashispiritnews.my.id/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface OrangApiService {
    @GET("/")
    suspend fun getMahasiswa(): String
}

object MahasiswaApi {
    val service: OrangApiService by lazy {
        retrofit.create(OrangApiService::class.java)
    }
}