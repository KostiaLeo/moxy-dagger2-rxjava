package com.example.viewstatemvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.view.MainView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class MainPresenter @Inject constructor(
    private val repository: Repository,
    private val context: Context,
    @Named("WorkScheduler") private val workScheduler: Scheduler,
    @Named("ResultScheduler") private val resultScheduler: Scheduler
) : MvpPresenter<MainView>() {

    private val compositeDisposable = CompositeDisposable()
    private var reactiveNetworkDisposable: Disposable? = null

    private val tag = "Presenter loading"

    var idlingResource: CountingIdlingResource? = null


    @SuppressLint("CheckResult")
    fun loadData() {
        // uncomment for UI testing
        idlingResource?.increment()
        viewState.showProgress()
        reactiveNetworkDisposable?.dispose()
        compositeDisposable.add(
            repository.retrieveData()
                .subscribeOn(workScheduler)
                .doOnSuccess {
                    if (it.isNotEmpty()) {
                        compositeDisposable.add(repository.saveData(it))
                    }
                }
                .observeOn(resultScheduler)
                .subscribe({ musicData ->
                    viewState.hideProgress()

                    if (musicData.isNotEmpty()) {
                        viewState.displayData(musicData)
                        // uncomment for UI testing
                        idlingResource?.decrement()
                    } else {
//                        Log.e(tag, "empty list retrieved, need to check internet connection")
                        if (reactiveNetworkDisposable == null) {
                            reactiveNetworkDisposable = waitForNetwork().also {
                                compositeDisposable.add(it)
                                viewState.showError()
                            }
                        }
                    }
                }, {
                    viewState.hideProgress()
                    viewState.showError()
                    // uncomment for UI testing
                    idlingResource?.decrement()
                    Log.e(tag, "data observing failed", it)
                })
        )
    }

    @SuppressLint("CheckResult")
    fun waitForNetwork(): Disposable =
        repository.getConnectivityObservable(context)
            .subscribeOn(workScheduler)
            .observeOn(resultScheduler)
            .timeout(10, TimeUnit.SECONDS)
            .subscribe({
                if (it.available()) {
                    loadData()
                }
            }, {
                // uncomment for UI testing
                idlingResource?.decrement()
                Log.e(tag, "failed network waiting", it)
            })

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

//        repositoryDisposable = repository.retrieveData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .retry(3)
//            .subscribe({
//                viewState.hideProgress()
//
//                if (it.isEmpty()) {
//                    viewState.showError()
//                    Log.e(tag, "empty list retrieved, need to check internet connection")
//                    networkConnectionDisposable = waitForNetwork()
//                    return@subscribe
//                } else {
//                    viewState.displayData(it)
//                    repository.saveData(it)
//                }
//            }, {
//                viewState.showError()
//                Log.e(tag, "data observing failed", it)
//            })
