package com.notepoint4u.hashtagassignment.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.notepoint4u.hashtagassignment.database.MusicDao
import java.lang.IllegalArgumentException

class MusicViewModelFactory(
    val databaseDao: MusicDao,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(MusicViewModel::class.java)){
           return MusicViewModel(databaseDao,application) as T
       }
        throw IllegalArgumentException("Unknown View model")
    }
}