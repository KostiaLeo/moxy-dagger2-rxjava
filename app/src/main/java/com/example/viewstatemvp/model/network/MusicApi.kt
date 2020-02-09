package com.example.viewstatemvp.model.network

import com.example.viewstatemvp.model.Music
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface MusicApi {
    @GET(NetworkUrl.URL_CODE)
    fun getDataObservable(): Single<Music>
}

interface NetworkUrl {
    companion object{
        const val BASE_URL = "https://api.myjson.com/"
        const val URL_CODE = "bins/u4988"
    }
}