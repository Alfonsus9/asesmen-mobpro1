package com.alfonsus0031.dompetku.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.R
import com.alfonsus0031.dompetku.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UndoScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.Back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.Trash)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        TrashScreenContent(
            innerPadding = innerPadding
        )
    }
}

@Composable
fun TrashScreenContent(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: UndoViewModel = viewModel(factory = factory)
    val transaksiList by viewModel.data.collectAsState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        HorizontalDivider()

        if (transaksiList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.No_transaction_data),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(transaksiList) {
                    TrashItem(
                        transaksi = it,
                        onUndo = {
                            viewModel.undoById(it.id)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TrashItem(transaksi: Transaksi, onUndo: () -> Unit) {
    val isIncome = transaksi.tipe == "pemasukan"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaksi.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
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
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Rp ${transaksi.nominal}",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = transaksi.tanggal,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(onClick = { onUndo() }) {
            Icon(
                imageVector = Icons.Default.Restore,
                contentDescription = "Undo",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}