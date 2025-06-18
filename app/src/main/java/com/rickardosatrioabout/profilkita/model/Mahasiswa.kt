package com.rickardosatrioabout.profilkita.model

import com.squareup.moshi.Json

data class Mahasiswa(
    val id: String,
    val nama: String,
    val kelas: String,
    val suku: String,
    @Json(name = "image") val gambar: String,
    val email_uploader: String
)
