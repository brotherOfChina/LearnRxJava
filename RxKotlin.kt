package com.example.administrator.learnofrxjava

import android.os.SystemClock
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by {zpj}
 *  on 2018/4/11 0011.
 */
class RxKotlin {
    companion object {
        fun createObservable() {

            val observable = Observable.defer {
                SystemClock.sleep(5000)
                Observable.just("one", "two", "three", "four", "five")
            }
            observable.observeOn(Schedulers.io()).subscribe(Consumer { Log.d("zpj", "" + it + Thread.currentThread()) })

        }
    }
}
