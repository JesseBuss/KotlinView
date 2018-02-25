package com.jbuss.kotlinview.presenter

import android.os.Bundle
import android.os.Parcelable

/**
 * Created by jbuss on 2/23/18.
 */
interface TimerPresenter {
    fun handleStartTimerClick()
    fun onTick(seconds: Int)
    fun handleLapButtonClick()
    fun handleResetButtonClick()
    fun onSaveInstanceState(parcel: Parcelable, bundle: Bundle) : Parcelable
    fun getSuperBundle(parcel: Parcelable?) : Parcelable?
    fun onRestoreInstanceState(parcel: Parcelable?)
}