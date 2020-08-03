package com.example.viewstatemvp.model.network

import com.example.viewstatemvp.model.RemoteSource
import com.example.viewstatemvp.model.Results
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val api: MusicApi
) : RemoteSource {

    override fun retrieveData(): Single<List<Results>> {
        return api.getDataObservable().map { it.results }
    }
}