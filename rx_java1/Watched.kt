package com.example.administrator.learnofrxjava.rx_java1

/**
 * Created by {zpj}
 * on 2018/4/11 0011.
 * 被观测者
 */

interface Watched {
    fun addWatcher(watcher: Watcher)
    fun removeWatcher(watcher: Watcher)
    fun notifyWatchers(s: String)
}
