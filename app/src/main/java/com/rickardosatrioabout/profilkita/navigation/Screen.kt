package com.rickardosatrioabout.profilkita.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")

    // Perbarui route untuk menyertakan ID
    data object Detail : Screen("detailScreen/{id}/{nama}/{kelas}/{suku}/{gambar}") {
        fun createRoute(id: String, nama: String, kelas: String, suku: String, gambar: String) =
            // Tambahkan ID ke dalam route
            "detailScreen/$id/$nama/$kelas/$suku/${gambar.replace("/", "%2F")}"
    }
}