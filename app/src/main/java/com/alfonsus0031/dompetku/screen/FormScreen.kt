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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alfonsus0031.dompetku.Model.RadioOption
import com.alfonsus0031.dompetku.R
import com.alfonsus0031.dompetku.helper.convertMillisToDateString
import com.alfonsus0031.dompetku.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navHostController: NavHostController,
    id: Long? = null
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var judul by remember { mutableStateOf("") }
    var nominal by remember { mutableStateOf("") }
    var tipe by remember { mutableStateOf("pemasukan") }
    var tanggal by remember { mutableStateOf("") }

    var judulError by remember { mutableStateOf(false) }
    var nominalError by remember { mutableStateOf(false) }
    var tanggalError by remember { mutableStateOf(false) }

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
                ),
                actions = {
                    IconButton(
                        onClick = {
                            val nominalValue = nominal.toIntOrNull()

                            judulError = judul.isBlank()
                            nominalError = nominalValue == null || nominalValue <= 0
                            tanggalError = tanggal.isBlank()

                            if (judulError || nominalError || tanggalError) return@IconButton

                            if (id == null) {
                                viewModel.insert(judul, nominalValue!!, tipe, tanggal)
                            } else {
                                viewModel.update(id, judul, nominalValue!!, tipe, tanggal)

                            }

                            navHostController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.Save),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        ScreenContent(
            innerPadding = innerPadding,
            judul = judul,
            onJudulChange = { judul = it },
            nominal = nominal,
            onNomChange = { nominal = it },
            tipe = tipe,
            onTipeChange = { tipe = it },
            tanggal = tanggal,
            onDateChange = { tanggal = it },
            judulError = judulError,
            nominalError = nominalError,
            tanggalError = tanggalError
        )
    }
}

@Composable
fun ScreenContent(
    innerPadding: PaddingValues,
    judul: String,
    onJudulChange: (String) -> Unit,
    nominal: String,
    onNomChange: (String) -> Unit,
    tipe: String,
    onTipeChange: (String) -> Unit,
    tanggal: String,
    onDateChange: (String) -> Unit,
    judulError: Boolean,
    nominalError: Boolean,
    tanggalError: Boolean
) {

    var showModal by remember { mutableStateOf(false) }

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
            onValueChange = { onJudulChange(it) },
            label = { Text(text = stringResource(R.string.Information)) },
            trailingIcon = { IconPicker(judulError, "txt") },
            supportingText = { ErrorHint(judulError) },
            isError = judulError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nominal,
            onValueChange = { onNomChange(it) },
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
                            onClick = { onTipeChange(option.value) },
                            role = Role.RadioButton
                        )
                        .padding(12.dp)
                )
            }
        }

        OutlinedTextField(
            value = tanggal,
            onValueChange = { },
            label = { Text(text = stringResource(R.string.Choose_Date)) },
            supportingText = { ErrorHint(tanggalError) },
            isError = tanggalError,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showModal = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (showModal) {
            DatePickerModal(
                onDateSelected = { millis ->
                    if (millis != null) {
                        onDateChange(convertMillisToDateString(millis))
                    }
                },
                onDismiss = { showModal = false }
            )
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

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}