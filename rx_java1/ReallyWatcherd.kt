package com.example.administrator.learnofrxjava.rx_java1

/**
 * Created by {zpj}
 *  on 2018/4/11 0011.
 */
class ReallyWatcherd : Watched {
    val list = mutableListOf<Watcher>()
    override fun addWatcher(watcher: Watcher) {
        list.add(watcher)
    }

    override fun removeWatcher(watcher: Watcher) {
        list.remove(watcher)
    }

    override fun notifyWatchers(s: String) {
        for (watcher in list) {
            watcher.updata(s)
        }
    }
}