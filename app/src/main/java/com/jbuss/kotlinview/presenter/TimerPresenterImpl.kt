package com.jbuss.kotlinview.presenter

import android.os.Bundle
import android.os.Parcelable
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jbuss.kotlinview.model.LapRow
import com.jbuss.kotlinview.model.OnTickListener
import com.jbuss.kotlinview.model.SecondsTimer
import com.jbuss.kotlinview.utils.getMmSsString
import com.jbuss.kotlinview.view.TimerView

/**
 * Created by jbuss on 2/23/18.
 */

class TimerPresenterImpl(private val timerView: TimerView, private val secondsTimer: SecondsTimer) : TimerPresenter,
        OnTickListener {
    private val EXTRA_SUPER= "super"
    private val EXTRA_SECONDS = "seconds"
    private val EXTRA_LAPS = "laps"

    var laps: ArrayList<LapRow>
    var currSeconds: Int? = null

    init {
        secondsTimer.setListener(this)
        laps = ArrayList()
    }

    override fun onTick(seconds: Int) {
        currSeconds = seconds
        timerView.updateTimer(currSeconds!!.getMmSsString())
    }

    override fun handleStartTimerClick() {
        secondsTimer.startTimer()
        timerView.setStartButtonVisibility(GONE)
    }

    override fun handleLapButtonClick() {
        if (currSeconds == null) {
            return
        }

        val lap: LapRow
        if (laps.size == 0) {
            timerView.setLapVisibility(VISIBLE)
            lap = LapRow(0, currSeconds!!, currSeconds!!)
        } else {
            val prevTime = laps[laps.size - 1].totalSeconds
            lap = LapRow(laps.size, currSeconds!!, currSeconds!! - prevTime)
        }
        laps.add(lap)

        timerView.displayRow(lap.toString())
    }

    override fun handleResetButtonClick() {
        timerView.clearRows()
        timerView.setLapVisibility(GONE)
        timerView.updateTimer(0.getMmSsString())
        timerView.setStartButtonVisibility(VISIBLE)

        secondsTimer.cancelTimer()
        laps.clear()
        currSeconds = null
    }

    override fun onSaveInstanceState(parcel: Parcelable, bundle: Bundle) : Parcelable {
        bundle.putParcelable(EXTRA_SUPER, parcel)
        currSeconds?.let { bundle.putInt(EXTRA_SECONDS, it) }
        bundle.putParcelableArrayList(EXTRA_LAPS, laps)
        return bundle
    }

    override fun getSuperBundle(parcel: Parcelable?): Parcelable? {
        if (parcel is Bundle) {
            return parcel.getBundle(EXTRA_SUPER)
        }

        return null
    }

    override fun onRestoreInstanceState(parcel: Parcelable?) {
        if (parcel is Bundle) {
            currSeconds = parcel.getInt(EXTRA_SECONDS, -1)
            if (currSeconds == -1) {
                currSeconds = null
            }

            laps = parcel.getParcelableArrayList(EXTRA_LAPS)
        }

        laps.forEach {
            timerView.setLapVisibility(VISIBLE)
            timerView.displayRow(it.toString())
        }

        currSeconds?.let {
            timerView.setStartButtonVisibility(GONE)
            timerView.updateTimer(currSeconds!!.getMmSsString())
            secondsTimer.startTimer(currSeconds!!)
        }
    }
}
