package com.alfonsus0031.dompetku.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alfonsus0031.dompetku.Model.RadioOption
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navHostController: NavHostController,
    transaksiList: MutableList<Transaksi>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.Back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.Add_Transaction))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->

        ScreenContent(
            innerPadding = innerPadding,
            transaksiList = transaksiList,
            kembali = { navHostController.popBackStack() }
        )
    }
}

@Composable
fun ScreenContent(
    innerPadding: PaddingValues,
    transaksiList: MutableList<Transaksi>,
    kembali: () -> Unit
) {
    var judul by remember { mutableStateOf("") }
    var nominal by remember { mutableStateOf("") }
    var tipe by remember { mutableStateOf("pemasukan") }
    var tanggal by remember { mutableStateOf("") }

    var judulError by remember { mutableStateOf(false) }
    var nominalError by remember { mutableStateOf(false) }
    var tanggalError by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        RadioOption("pemasukan", stringResource(R.string.Income)),
        RadioOption("pengeluaran", stringResource(R.string.Expense))
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text(text = stringResource(R.string.Information)) },
            trailingIcon = { IconPicker(judulError, "txt") },
            supportingText = { ErrorHint(judulError) },
            isError = judulError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nominal,
            onValueChange = { nominal = it },
            label = { Text(stringResource(R.string.Nominal)) },
            trailingIcon = { IconPicker(nominalError, "Rp.") },
            supportingText = { ErrorHint(nominalError) },
            isError = nominalError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            radioOptions.forEach { option ->
                Option(
                    label = option.label,
                    isSelected = tipe == option.value,
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = tipe == option.value,
                            onClick = { tipe = option.value },
                            role = Role.RadioButton
                        )
                        .padding(12.dp)
                )
            }
        }

        OutlinedTextField(
            value = tanggal,
            onValueChange = { tanggal = it },
            label = { Text("Tanggal (yyyy-MM-dd)") },
            trailingIcon = { IconPicker(tanggalError, "date") },
            supportingText = { ErrorHint(tanggalError) },
            isError = tanggalError,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("2026-04-05") }
        )

        Button(
            onClick = {
                val nominalValue = nominal.toIntOrNull()

                judulError = judul.isBlank()
                nominalError = nominalValue == null || nominalValue <= 0
                tanggalError = tanggal.isBlank()

                if (judulError || nominalError || tanggalError) {
                    return@Button
                }

                val transaksi = Transaksi(
                        judul = judul,
                        nominal = nominalValue!!,
                        tipe = tipe,
                        tanggal = tanggal
                    )

                    transaksiList.add(transaksi)
                    kembali()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun Option(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.Input_invalid))
    }
}

