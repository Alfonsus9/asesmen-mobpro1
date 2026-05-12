package com.alfonsus0031.dompetku.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfonsus0031.dompetku.database.TransaksiDb
import com.alfonsus0031.dompetku.screen.DetailViewModel
import com.alfonsus0031.dompetku.screen.MainViewModel
import com.alfonsus0031.dompetku.screen.UndoViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = TransaksiDb.getInstance(context).dao
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }else if (modelClass.isAssignableFrom(UndoViewModel::class.java)) {
            return UndoViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}