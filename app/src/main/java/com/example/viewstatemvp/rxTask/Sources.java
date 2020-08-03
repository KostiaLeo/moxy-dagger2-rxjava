package com.example.viewstatemvp.rxTask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Sources {
    static Observable<Boolean> observable1 = Observable.interval(600, TimeUnit.MILLISECONDS).timeInterval().create(s -> {
        s.onNext(true);
        s.onNext(false);
        s.onNext(true);
        s.onError(new NullPointerException());
        s.onNext(true);
        s.onNext(false);
        s.onComplete();
    });

    static Observable<Boolean> observable2 = Observable.interval(400, TimeUnit.MILLISECONDS).create(s -> {
        s.onNext(false);
        s.onError(new NullPointerException());
        s.onNext(false);
        s.onNext(false);
        s.onNext(true);
        s.onNext(true);
        s.onNext(true);
        s.onComplete();
    });

    static Observable<Integer> numberObservable = Observable.interval(500, TimeUnit.MILLISECONDS).create(s -> {
        Random random = new Random();
        while (!s.isDisposed()) {
            s.onNext(random.nextInt(5));
        }
    });
}
