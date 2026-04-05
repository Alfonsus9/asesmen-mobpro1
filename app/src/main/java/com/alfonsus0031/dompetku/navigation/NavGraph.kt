package com.alfonsus0031.dompetku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.screen.AboutScreen
import com.alfonsus0031.dompetku.screen.FormScreen
import com.alfonsus0031.dompetku.screen.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController(),
    transaksiList: SnapshotStateList<Transaksi>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController, transaksiList = transaksiList)
        }

        composable(route = Screen.Form.route) {
            FormScreen(navController, transaksiList = transaksiList)
        }

        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
    }
}