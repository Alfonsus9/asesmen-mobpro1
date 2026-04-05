package com.alfonsus0031.dompetku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.compose.rememberNavController
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.navigation.SetupNavGraph
import com.alfonsus0031.dompetku.ui.theme.DompetKuTheme

class MainActivity : ComponentActivity() {
    val transaksiList = mutableStateListOf<Transaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DompetKuTheme {
                SetupNavGraph(rememberNavController(), transaksiList)
            }
        }
    }
}