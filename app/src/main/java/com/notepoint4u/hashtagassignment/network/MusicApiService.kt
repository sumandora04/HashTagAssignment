package com.notepoint4u.hashtagassignment.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface MusicApiService {

    @GET("bins/rov51")
    fun getMusicListAsync(): Deferred<MusicData>
}


