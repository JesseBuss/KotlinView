package com.jbuss.kotlinview.view

/**
 * Created by jbuss on 2/23/18.
 */
interface TimerView {
    fun setStartButtonVisibility(visibility: Int)
    fun setLapVisibility(visibility: Int)
    fun updateTimer(textToDisplay: String)
    fun displayRow(textToDisplay: String)
    fun clearRows()
}