package com.notepoint4u.hashtagassignment.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.notepoint4u.hashtagassignment.database.MusicDao
import com.notepoint4u.hashtagassignment.network.MusicApiClient
import com.notepoint4u.hashtagassignment.network.MusicDetail
import kotlinx.coroutines.*

private const val TAG = "MusicViewModel"

class MusicViewModel(
    private val databaseDao: MusicDao,
    application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val job = Job()
    private val coroutineScope: CoroutineScope = CoroutineScope(job + Dispatchers.Main)


    fun getMusicListFromDb():LiveData<List<MusicDetail>>? {
       return databaseDao.getMusicList()
    }


    private fun getAllMusicList() {
        coroutineScope.launch {
            val deferredMusic = MusicApiClient.retrofitService.getMusicListAsync()

            try {
                val musicDataFromDeferred = deferredMusic.await()
                if (musicDataFromDeferred.musicData.isNotEmpty()) {
                    onClearDatabase()
                    for (music in musicDataFromDeferred.musicData) {
                        onFillingDatabase(music)
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
            }
        }
    }

    init {
        if (checkInternetConnectivity()) {
            getAllMusicList()
        } else {
           Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            databaseDao.clearTable()
        }
    }

    private suspend fun insert(music: MusicDetail) {
        withContext(Dispatchers.IO) {
            databaseDao.insertSongs(music)
        }
    }

    fun onClearDatabase() {
        coroutineScope.launch {
            // Clear the database table.
            clearDatabase()
        }
    }

    fun onFillingDatabase(newMusic: MusicDetail) {
        coroutineScope.launch {
            insert(newMusic)
        }
    }

    private fun checkInternetConnectivity(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

}