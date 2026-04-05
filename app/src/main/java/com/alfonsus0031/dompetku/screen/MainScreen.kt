package com.alfonsus0031.dompetku.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (transaksiList: SnapshotStateList<Transaksi>) {
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        ScreenContent (
            innerPadding = innerPadding,
            transaksiList = transaksiList,
            onTambahClick = {}
        )
    }
}

@Composable
fun ScreenContent(
    innerPadding: PaddingValues,
    transaksiList: List<Transaksi>,
    onTambahClick: () -> Unit
) {

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
            .verticalScroll(rememberScrollState())
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


            Button(
                onClick = onTambahClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.Add_Transaction))
            }
        }

        HorizontalDivider()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (transaksiList.isEmpty()) {
                Text(
                    text = stringResource(R.string.No_transaction_data),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column {
                    transaksiList.forEach { transaksi ->

                        TransaksiItem(transaksi)

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun TransaksiItem(transaksi: Transaksi) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
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