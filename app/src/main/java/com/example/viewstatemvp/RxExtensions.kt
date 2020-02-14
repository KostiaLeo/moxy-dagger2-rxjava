package com.example.viewstatemvp

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
fun <T> Single<T>.launchBackgroundTask(
    blockOnNext: (t: T) -> Unit,
    blockOnError: (throwable: Throwable) -> Unit
): Disposable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .retry(3)
        .subscribe({
            blockOnNext(it)
        }, {
            blockOnError(it)
        })
}

@SuppressLint("CheckResult")
fun <T> Observable<T>.launchBackgroundTask(
    blockOnNext: (t: T) -> Unit,
    blockOnError: (throwable: Throwable) -> Unit
): Disposable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .retry(3)
        .subscribe({
            blockOnNext(it)
        }, {
            blockOnError(it)
        })
}

fun <T> createRxSingle(sourceBlock: () -> T): Single<T> =
    Single.fromCallable { sourceBlock() }

//sealed class ResponseState {
//    object Loading: ResponseState
//
//    data class Content(): ResponseState() {
//
//    }
//}