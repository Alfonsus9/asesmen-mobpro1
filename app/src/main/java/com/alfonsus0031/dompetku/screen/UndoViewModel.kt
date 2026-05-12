package com.alfonsus0031.dompetku.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.database.TransaksiDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UndoViewModel(private val dao: TransaksiDao) : ViewModel() {

    val data: StateFlow<List<Transaksi>> = dao.getDeletedTransaksi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun undoById(id: Long) {
        viewModelScope.launch {
            dao.undoById(id)
        }
    }

}