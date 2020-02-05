package com.example.viewstatemvp.model

import com.example.viewstatemvp.model.network.Music
import com.example.viewstatemvp.model.network.MusicApi
import io.reactivex.Flowable
import javax.inject.Inject

class ModelImpl @Inject constructor(
    private val api: MusicApi
) : Model {

    override fun loadData(): Flowable<Music> {
        return api.getDataObservable()
    }
}