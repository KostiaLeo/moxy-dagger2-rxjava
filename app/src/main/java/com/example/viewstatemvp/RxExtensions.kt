@file:JvmName("rx")
package com.example.viewstatemvp

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
inline fun <T> Single<T>.launchBackgroundTask(
    crossinline blockOnNext: (t: T) -> Unit,
    crossinline blockOnError: (throwable: Throwable) -> Unit
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
inline fun <T> Observable<T>.launchBackgroundTask(
    crossinline blockOnNext: (t: T) -> Unit,
    crossinline blockOnError: (throwable: Throwable) -> Unit
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

inline fun <T> createRxSingle(crossinline sourceBlock: () -> T): Single<T> =
    Single.fromCallable { sourceBlock() }

//sealed class ResponseState {
//    object Loading: ResponseState
//
//    data class Content(): ResponseState() {
//
//    }
//}