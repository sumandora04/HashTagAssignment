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

    @Query("SELECT DISTINCT artistName FROM music_detail_table ORDER BY musicTrackId ASC")
    fun getArtist(): LiveData<List<String>>?

    @Query("SELECT DISTINCT albumName FROM music_detail_table ORDER BY musicTrackId ASC")
    fun getAlbum(): LiveData<List<String>>?


    @Query("SELECT DISTINCT musicTackName FROM music_detail_table where artistName =:artist ORDER BY musicTrackId ASC")
    fun getMusicTrackForArtist(artist:String): LiveData<List<String>>?

    @Query("SELECT DISTINCT musicTackName FROM music_detail_table where albumName =:album ORDER BY musicTrackId ASC")
    fun getMusicTrackForAlbum(album:String): LiveData<List<String>>?


}