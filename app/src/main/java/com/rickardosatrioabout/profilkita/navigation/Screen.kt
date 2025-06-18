package com.rickardosatrioabout.profilkita.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object Detail : Screen("detailScreen/{nama}/{kelas}/{suku}/{gambar}") {
        fun createRoute(nama: String, kelas: String, suku: String, gambar: String) =
            "detailScreen/$nama/$kelas/$suku/${gambar.replace("/", "%2F")}"
    }
}