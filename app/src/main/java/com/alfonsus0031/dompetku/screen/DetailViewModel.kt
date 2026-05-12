package com.alfonsus0031.dompetku.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfonsus0031.dompetku.Model.Transaksi
import com.alfonsus0031.dompetku.database.TransaksiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: TransaksiDao) : ViewModel() {

    fun insert(judul: String, nominal: Int, tipe: String, tanggal: String) {
        val transaksi = Transaksi(
            judul = judul,
            nominal = nominal,
            tipe = tipe,
            tanggal = tanggal
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(transaksi)
        }
    }

    suspend fun getTransaksi(id: Long): Transaksi? {
        return dao.getTransaksiById(id)
    }

    fun update(id: Long, judul: String, nominal: Int, tipe: String, tanggal: String) {
        val transaksi = Transaksi(
            id = id,
            judul = judul,
            nominal = nominal,
            tipe = tipe,
            tanggal = tanggal
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(transaksi)
        }
    }
}