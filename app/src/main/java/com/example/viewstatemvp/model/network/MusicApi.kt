package com.example.viewstatemvp.model.network

import io.reactivex.Flowable
import retrofit2.http.GET

interface MusicApi {
    @GET(NetworkUrl.URL_CODE)
    fun getDataObservable(): Flowable<Music>
}