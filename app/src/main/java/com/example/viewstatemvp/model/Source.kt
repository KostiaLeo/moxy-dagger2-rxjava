package com.example.viewstatemvp.model

import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface Source {
    fun retrieveData(): Single<List<Results>>
}

interface LocalSource: Source {
    fun refreshData(newData: List<Results>): Disposable
}

interface RemoteSource: Source

interface Repository {
    fun retrieveData(): Single<List<Results>>
    fun saveData(newData: List<Results>): Disposable
}