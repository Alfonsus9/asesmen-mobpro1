package com.alfonsus0031.dompetku.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val nominal: Int,
    val tipe: String,
    val tanggal: String
)
