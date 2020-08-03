package com.example.viewstatemvp.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
    private val networkDispatcher: NetworkConnectionDispatcher
) : Repository {

    override fun retrieveData(): Single<List<Results>> {
        return if (networkDispatcher.isConnectedToInternet)
            remoteSource.retrieveData().onErrorResumeNext(localSource.retrieveData())
        else
            localSource.retrieveData()
    }

    override fun saveData(newData: List<Results>): Disposable {
        return localSource.refreshData(newData)
    }

    override fun connectivityObservable(context: Context): Observable<Connectivity> {
        return ReactiveNetwork.observeNetworkConnectivity(context)
    }
}


class NetworkConnectionDispatcher @Inject constructor(private val applicationContext: Context) {

    val isConnectedToInternet: Boolean
        @SuppressLint("MissingPermission")
        get() {
            val conManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conManager.activeNetworkInfo
            return network != null && network.isConnected
        }
}