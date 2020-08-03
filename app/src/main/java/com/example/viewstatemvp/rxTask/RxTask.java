package com.example.viewstatemvp.rxTask;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
public class RxTask {

    private Observable<Boolean> obs1 = Sources.observable1;
    private Observable<Boolean> obs2 = Sources.observable2;

    @SuppressLint("CheckResult")
    public static void main(String[] args) {
        new RxTask().launch();
    }

    private Observable<Integer> numberObservable = Observable.interval(500, TimeUnit.MILLISECONDS).create(s -> {
        Random random = new Random();
        while (!s.isDisposed()) {
            s.onNext(random.nextInt(5));
        }
    });

    private void launch() {
        Observable.combineLatest(obs1, obs2, (boolA, boolB) -> boolA && boolB).doAfterNext(bool -> someVoid()).onErrorReturnItem(false)
                .switchMap(resBoolean -> {
                    if (resBoolean) {
                        return Observable.create(subscriber -> numberObservable.filter(num -> num % 2 == 0).take(1)
                                .subscribeOn(Schedulers.computation()).map(num -> Double.toString(1.0 / num))
                                .subscribe(subscriber::onNext, error -> Log.e("stream", "failed subscribing on number", error)));
                    } else {
                        return Observable.just("");
                    }
                }).subscribeOn(Schedulers.newThread())
                .doOnNext(result -> Log.d("log", "Ура!" + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io()).subscribe();
    }

    private void someVoid(){}
}