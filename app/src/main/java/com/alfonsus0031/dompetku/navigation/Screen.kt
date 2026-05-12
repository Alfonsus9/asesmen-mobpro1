package com.alfonsus0031.dompetku.navigation

const val KEY_ID_CATATAN = "idTransaksi"
sealed class Screen (val route: String ) {
    data object Home: Screen("mainScreen")
    data object  Form: Screen("fromScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_CATATAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object  About: Screen("aboutScreen")
}