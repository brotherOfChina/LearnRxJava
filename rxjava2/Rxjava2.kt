package com.example.administrator.learnofrxjava.rxjava2

import java.util.*

/**
 * Created by {zpj}
 *  on 2018/4/11 0011.
 */
class SampleWatcher(sampleWatchered: SampleWatchered) : Observer {
    init {
        sampleWatchered.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        print("打印数据" + (p0 as SampleWatchered).getData())
    }
}

class SampleWatchered : Observable() {
    private var data = 0
    fun setData(data: Int) {
        if (this.data != data) {
            this.data = data
            setChanged()
            notifyObservers()
        }


    }

    fun getData(): Int = data


}