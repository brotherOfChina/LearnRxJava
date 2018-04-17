package com.example.administrator.learnofrxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.administrator.learnofrxjava.rxjava2.TestClass2
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val list = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add("normal")
        list.add("map")
        list.add("thread")
        list.add("flatMap")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        list_view.adapter = adapter
        adapter.addAll(list)
        list_view.onItemClickListener = AdapterView.OnItemClickListener { i: AdapterView<*>?, _: View, positon: Int, l: Long ->
            run {
                when (positon) {
                    0 -> normal()
                    1 -> map()
                    2 -> thread()
                    3 -> flatMap()
                }

            }
        }
    }

    fun flatMap() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(11)
            e.onNext(2)
            e.onNext(3)
        }

        ).flatMap { t ->
            val stringList = ArrayList<String>()
            for (i in 0..2) {
                stringList.add("我是第" + i + "个" + t)
            }
            Observable.fromIterable(stringList).delay(10, TimeUnit.SECONDS)
        }.subscribe { s -> Log.i(TAG, "accept: $s") }
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
        })
                .concatMap { t ->
                    Observable.create(ObservableOnSubscribe<String> { e -> e.onNext(t.toString()) })
                }.subscribe { s ->
                    Log.i(TAG, "自己手写的: " + s);
                }
    }

    fun map() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onNext(4)
        }).map { t -> "第" + t.toString() }.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(integer: String) {
                Log.d("zpj", integer)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }

    fun normal() {
        TestClass2.test2()
        Observable.create(ObservableOnSubscribe<Int> { e -> e.onNext(12) }).subscribe { t: Int? -> Log.d("zpj", t.toString()) }
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
        }).subscribe(object : Observer<Int> {
            override fun onNext(t: Int) {
                Log.d("zpj", t.toString())
            }

            override fun onSubscribe(d: Disposable) {

            }


            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })

    }

    fun thread() {
        Observable.create<String> { e ->

            e.onNext("发送事件1${Thread.currentThread()}")
            e.onNext("发送事件2")
            e.onNext("发送事件3")
            e.onNext("发送事件4")
            e.onNext("完成")
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { s ->

                    Log.i(TAG, "doOnnext: ${s} ${Thread.currentThread()}");
                }
                .filter {
                    "发送事件2" == it
                }
                .subscribe { s ->
                    Log.i(TAG, "thred:$s +${Thread.currentThread()}");
                }
    }

}
