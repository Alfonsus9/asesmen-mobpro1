package com.alfonsus0031.dompetku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alfonsus0031.dompetku.Model.Transaksi
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaksiDao {
    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi WHERE is_Delete = 0 ORDER BY tanggal DESC")
    fun getTransaksi(): Flow<List<Transaksi>>

    @Query("SELECT * FROM transaksi WHERE id = :id")
    suspend fun getTransaksiById(id: Long): Transaksi?

    @Query("UPDATE transaksi SET is_Delete = 1 WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE transaksi SET is_Delete = 0 WHERE id = :id")
    suspend fun undoById(id: Long)

    @Query("SELECT * FROM transaksi WHERE is_Delete = 1 ORDER BY tanggal DESC")
    fun getDeletedTransaksi(): Flow<List<Transaksi>>
}