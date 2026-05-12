package com.alfonsus0031.dompetku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.screen.AboutScreen
import com.alfonsus0031.dompetku.screen.FormScreen
import com.alfonsus0031.dompetku.screen.MainScreen
import com.alfonsus0031.dompetku.screen.UndoScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }

        composable(route = Screen.Form.route) {
            FormScreen(navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            FormScreen(navController, id)
        }

        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }

        composable(Screen.Undo.route) {
            UndoScreen(navController = navController)
        }
    }
}