package com.alfonsus0031.dompetku.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.R
import com.alfonsus0031.dompetku.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.MainScreen_description))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(Screen.About.route)}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Form.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.Add_Transaction),
                )
            }
        }

    ) { innerPadding ->
        ScreenContent (
            innerPadding = innerPadding,
        )
    }
}

@Composable
fun ScreenContent(
    innerPadding: PaddingValues,
) {

    val viewModel: MainViewModel = viewModel()
    val transaksiList = viewModel.data
    val context = LocalContext.current

    val totalPemasukkan = transaksiList
        .filter { it.tipe == "pemasukan" }
        .sumOf { it.nominal }

    val totalPengeluaran = transaksiList
        .filter { it.tipe == "pengeluaran" }
        .sumOf { it.nominal }

    val totalUang = totalPemasukkan - totalPengeluaran

    val warna = if (totalUang >= 0) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = stringResource(R.string.Total_Balance, totalUang),
                color = warna,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.Income_main, totalPemasukkan),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = stringResource(R.string.Expense_main, totalPengeluaran),
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            val message = stringResource(R.string.laporan_keuangan_template, totalUang, totalPemasukkan, totalPengeluaran)

            Button(
                onClick = { shareData(context, message) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.Share))
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            if (transaksiList.isEmpty()) {

                item {

                    Box(
                        modifier = Modifier.fillParentMaxSize()
                    ) {

                        Text(
                            text = stringResource(R.string.No_transaction_data),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

            } else {

                items(transaksiList) {
                    TransaksiItem (transaksi = it) {

                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TransaksiItem(transaksi: Transaksi, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(transaksi.judul)

            val isIncome = transaksi.tipe == "pemasukan"

            Text(
                text = if (isIncome)
                    stringResource(R.string.Income)
                else
                    stringResource(R.string.Expense),

                color = if (isIncome)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,

                fontWeight = FontWeight.Bold
            )
        }

        Column {
            Text("Rp ${transaksi.nominal}")
            Text(transaksi.tanggal)
        }
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}