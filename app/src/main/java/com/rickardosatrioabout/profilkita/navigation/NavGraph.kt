package com.rickardosatrioabout.profilkita.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rickardosatrioabout.profilkita.ui.theme.screen.DetailScreen
import com.rickardosatrioabout.profilkita.ui.theme.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("nama") { type = NavType.StringType },
                navArgument("kelas") { type = NavType.StringType },
                navArgument("suku") { type = NavType.StringType },
                navArgument("gambar") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val nama = backStackEntry.arguments?.getString("nama") ?: ""
            val kelas = backStackEntry.arguments?.getString("kelas") ?: ""
            val suku = backStackEntry.arguments?.getString("suku") ?: ""
            val gambar = backStackEntry.arguments?.getString("gambar")?.replace("%2F", "/") ?: ""

            DetailScreen(
                id = id,
                nama = nama,
                kelas = kelas,
                suku = suku,
                gambar = gambar,
                navController = navController
            )
        }
    }
}