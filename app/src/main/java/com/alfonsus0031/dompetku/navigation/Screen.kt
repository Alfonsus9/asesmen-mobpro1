package com.alfonsus0031.dompetku.navigation

sealed class Screen (val route: String ) {
    data object Home: Screen("mainScreen")
}