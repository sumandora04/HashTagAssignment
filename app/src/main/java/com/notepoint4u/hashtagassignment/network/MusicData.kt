package com.notepoint4u.hashtagassignment.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class MusicData(
    @Json(name = "data") val musicData: List<MusicDetail>
)


@Entity(tableName = "music_detail_table")
data class MusicDetail(
    @PrimaryKey(autoGenerate = true)
    var musicTrackId: Long = 0L,
    @Json(name = "Name") val musicTackName: String,
    @Json(name = "Artist") val artistName: String,
    @Json(name = "Album") val albumName: String
) {

}
