package com.jbuss.kotlinview.model

import java.util.*

/**
 * Created by jbuss on 2/23/18.
 */
open class SecondsTimer {
    private var timer : Timer? = null
    private var listener : OnTickListener? = null

    fun setListener(listener: OnTickListener) {
        this.listener = listener
    }

    fun startTimer(startAt : Int = 0) {
        timer = Timer()

        timer!!.scheduleAtFixedRate(MyTimerTask(listener, startAt), 0, 1000)
    }

    fun cancelTimer() {
        timer?.cancel()
    }

    private class MyTimerTask(private val listener: OnTickListener?, private var seconds: Int) : TimerTask() {

        override fun run() {
            listener?.onTick(seconds++)
        }
    }
}