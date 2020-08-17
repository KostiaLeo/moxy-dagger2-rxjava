package com.example.viewstatemvp.model

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
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
    fun getConnectivityObservable(context: Context): Observable<Connectivity>
}