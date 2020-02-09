package com.example.viewstatemvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.viewstatemvp.model.LocalSource
import com.example.viewstatemvp.model.RemoteSource
import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.view.MainView
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val repository: Repository
) : MvpPresenter<MainView>() {

    private lateinit var repositoryDisposable: Disposable
    private val tag = "Presenter loading"

    @SuppressLint("CheckResult")
    fun loadData() {
        viewState.showProgress()

        repositoryDisposable = repository.retrieveData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(3)
            .subscribe({
                viewState.hideProgress()

                if (it.isEmpty()) {
                    viewState.showError()
                    Log.e(tag, "empty list retrieved, need to check internet connection")
                    return@subscribe
                } else {
                    viewState.displayData(it)
                    repository.saveData(it)
                }
            }, {
                viewState.showError()
                Log.e(tag, "data observing failed", it)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        with(repositoryDisposable) {
            if (!isDisposed) dispose()
        }
    }
}