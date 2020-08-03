package com.example.viewstatemvp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class RxLearning {

    public void launch() {
        Observable<String> migosObservable = createMigosObs();
        log("started");
        migosObservable
                .doOnNext(s -> log("firstDoOnNext"))
                .flatMap(s -> Observable.just("Migos: " + s)
                        .doOnNext(s1 -> log("flatMap the " + s1))
                        //.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                )
                .doOnNext(s -> log("Mapped"))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    log("got" + s);
                });
        log("finished");
    }

    Observable<String> createMigosObs() {
        return Observable.create(sub -> {
            ArrayList<String> songs = new ArrayList<>();
            songs.add("Walk It Talk It");
            songs.add("WOA");
            songs.add("Bad and Boujee");
            songs.add("Pure Water");
            songs.add("Notice Me");
            songs.forEach(sub::onNext);
        });
    }


    private String log(String message) {
        //System.out.println("logThread|" + Thread.currentThread().getName() + "| " + message);
        Log.d("logThread", "|" + Thread.currentThread().getName() + "| " + message);
        return message;
    }
}
