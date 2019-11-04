package com.notepoint4u.hashtagassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.notepoint4u.hashtagassignment.network.MusicDetail

@Dao
interface MusicDao {
    @Insert
    fun insertSongs(music:MusicDetail)


    @Query("DELETE FROM music_detail_table")
    fun clearTable()

    @Query("SELECT * FROM music_detail_table ORDER BY musicTrackId desc")
    fun getMusicList(): LiveData<List<MusicDetail>>?
}