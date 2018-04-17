package com.example.administrator.learnofrxjava.rxjava2;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by {zpj}
 * on 2018/4/16 0016.
 */

public class TestClass2 {

    private static final String TAG = "Test2";

    public static void test2() {

        Observable<Long> observable = Observable.interval(1, 1, java.util.concurrent.TimeUnit.DAYS);
        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.i(TAG, "onNext: " + aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "第" + integer.toString();
            }
        }).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Observable.create(new ObservableOnSubscribe<Integer>() {
                              @Override
                              public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                  e.onNext(11);
                                  e.onNext(2);
                                  e.onNext(3);
                              }
                          }

        ).flatMap(value -> {
            final List<String> stringList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                stringList.add("我是第" + i + "个" + value);
            }
            return Observable.fromIterable(stringList).delay(10, TimeUnit.SECONDS);
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept: "+s);
            }
        });
    }
}
