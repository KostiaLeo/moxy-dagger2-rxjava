package com.example.viewstatemvp.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.viewstatemvp.model.Model
import com.example.viewstatemvp.view.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val model: Model
) : MvpPresenter<MainView>() {

    // TODO: Rx, DataBinding, Room, DiffUtil

    @SuppressLint("CheckResult")
    fun loadData() {
        model.loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.results.asList() }
            .subscribe({
                viewState.displayData(it)
            }, {
                Log.e("Presenter loading", "data observing failed", it)
            })
    }
}