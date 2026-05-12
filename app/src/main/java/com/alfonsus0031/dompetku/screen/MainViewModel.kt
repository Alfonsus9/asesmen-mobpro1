package com.alfonsus0031.dompetku.screen

import androidx.lifecycle.ViewModel
import com.alfonsus0031.dompetku.Model.Transaksi

class MainViewModel : ViewModel() {

    val data = listOf(
        Transaksi(
            "Gaji Bulanan",
            5000000,
            "pemasukan",
            "2026-05-01"
        ),
        Transaksi(
            "Beli Makanan",
            45000,
            "pengeluaran",
            "2026-05-02"
        ),
        Transaksi(
            "Bayar Internet",
            350000,
            "pengeluaran",
            "2026-05-03"
        ),
        Transaksi(
            "Freelance Desain",
            1200000,
            "pemasukan",
            "2026-05-05"
        ),
        Transaksi(
            "Ngopi Bareng Teman",
            75000,
            "pengeluaran",
            "2026-05-06"
        ),
        Transaksi(
            "Top Up E-Wallet",
            200000,
            "pengeluaran",
            "2026-05-07"
        ),

        // Tambahan 20 data
        Transaksi(
            "Jual Barang Bekas",
            300000,
            "pemasukan",
            "2026-05-08"
        ),
        Transaksi(
            "Belanja Bulanan",
            500000,
            "pengeluaran",
            "2026-05-08"
        ),
        Transaksi(
            "Bonus Kantor",
            1500000,
            "pemasukan",
            "2026-05-09"
        ),
        Transaksi(
            "Bayar Listrik",
            250000,
            "pengeluaran",
            "2026-05-09"
        ),
        Transaksi(
            "Makan Siang",
            35000,
            "pengeluaran",
            "2026-05-10"
        ),
        Transaksi(
            "Investasi Saham",
            1000000,
            "pengeluaran",
            "2026-05-10"
        ),
        Transaksi(
            "Dividen Saham",
            450000,
            "pemasukan",
            "2026-05-11"
        ),
        Transaksi(
            "Bayar Spotify",
            55000,
            "pengeluaran",
            "2026-05-11"
        ),
        Transaksi(
            "Transportasi",
            40000,
            "pengeluaran",
            "2026-05-12"
        ),
        Transaksi(
            "THR",
            2500000,
            "pemasukan",
            "2026-05-12"
        ),
        Transaksi(
            "Beli Buku",
            120000,
            "pengeluaran",
            "2026-05-13"
        ),
        Transaksi(
            "Jasa Coding",
            800000,
            "pemasukan",
            "2026-05-13"
        ),
        Transaksi(
            "Bayar Air",
            95000,
            "pengeluaran",
            "2026-05-14"
        ),
        Transaksi(
            "Makan Malam",
            60000,
            "pengeluaran",
            "2026-05-14"
        ),
        Transaksi(
            "Cashback Marketplace",
            85000,
            "pemasukan",
            "2026-05-15"
        ),
        Transaksi(
            "Beli Sepatu",
            450000,
            "pengeluaran",
            "2026-05-15"
        ),
        Transaksi(
            "Servis Motor",
            175000,
            "pengeluaran",
            "2026-05-16"
        ),
        Transaksi(
            "Pendapatan YouTube",
            950000,
            "pemasukan",
            "2026-05-16"
        ),
        Transaksi(
            "Donasi",
            100000,
            "pengeluaran",
            "2026-05-17"
        ),
        Transaksi(
            "Hadiah Ulang Tahun",
            700000,
            "pemasukan",
            "2026-05-17"
        )
    )
}