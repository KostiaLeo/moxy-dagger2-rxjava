package com.example.viewstatemvp.model

import com.example.viewstatemvp.model.network.Music
import io.reactivex.Flowable

interface Model {
    fun loadData(): Flowable<Music>
}