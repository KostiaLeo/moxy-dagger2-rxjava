package com.example.viewstatemvp.model.database

import android.annotation.SuppressLint
import android.util.Log
import com.example.viewstatemvp.createRxSingle
import com.example.viewstatemvp.launchBackgroundTask
import com.example.viewstatemvp.model.LocalSource
import com.example.viewstatemvp.model.Results
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val dao: MusicDao
) : LocalSource {

    private val tag = "Database refreshing"

    override fun retrieveData(): Single<List<Results>> =
        dao.retrieveMusicData()

    @SuppressLint("CheckResult")
    override fun refreshData(newData: List<Results>): Disposable =
        dao.insertMusic(newData)
            .launchBackgroundTask({
                Log.d(tag, "Inserted totally ${it.size} items")
            }, {
                Log.e(tag, "${it.message}", it)
            })
}