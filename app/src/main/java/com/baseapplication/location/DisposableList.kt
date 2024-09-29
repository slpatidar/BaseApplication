package com.baseapplication.location

import io.reactivex.disposables.Disposable
import java.util.Collections

class DisposableList() {
    private val disposables: MutableList<Disposable> = Collections.synchronizedList(ArrayList())
    fun add(d: Disposable) {
        disposables.add(d)
    }

    fun remove(d: Disposable) {
        disposables.remove(d)
    }

    fun dispose() {
        synchronized(disposables) {
            val iterator: Iterator<Disposable> = disposables.iterator()
            while (iterator.hasNext()) {
                iterator.next().dispose()
            }
            disposables.clear()
        }
    }
}
