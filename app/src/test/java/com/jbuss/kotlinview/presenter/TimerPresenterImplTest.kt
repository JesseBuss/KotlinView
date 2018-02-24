package com.jbuss.kotlinview.presenter

import android.os.Bundle
import android.os.Parcelable
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jbuss.kotlinview.model.LapRow
import com.jbuss.kotlinview.model.SecondsTimer
import com.jbuss.kotlinview.utils.getMmSsString
import com.jbuss.kotlinview.view.TimerView
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

/**
 * Created by jbuss on 2/24/18.
 */
class TimerPresenterImplTest {
    lateinit var presenter : TimerPresenterImpl
    lateinit var view : TimerView
    lateinit var timer : SecondsTimer

    @Before
    fun setUp() {
        view = mock(TimerView :: class.java)
        timer = mock(SecondsTimer :: class.java)
        presenter = TimerPresenterImpl(view, timer)
    }

    @Test
    fun testOnTick() {
        presenter.onTick(22)
        assertEquals(22, presenter.currSeconds)
        verify(view).updateTimer(22.getMmSsString())
    }

    @Test
    fun handleStartTimerClick() {
        presenter.handleStartTimerClick()

        verify(view).setStartButtonVisibility(GONE)
    }

    @Test
    fun handleLapButtonClickNotStarted() {
        presenter.handleLapButtonClick()
        assertEquals(0, presenter.laps.size)
        verifyZeroInteractions(view)
    }

    @Test
    fun handleLapButtonClick() {
        val expectedLapRow = LapRow(0, 22, 22)
        presenter.currSeconds = 22

        presenter.handleLapButtonClick()

        assertEquals(1, presenter.laps.size)
        assertEquals(expectedLapRow, presenter.laps[0])
        verify(view).displayRow(expectedLapRow.toString())
    }

    @Test
    fun handleResetButtonClick() {
        presenter.currSeconds = 55
        presenter.laps.add(LapRow(0, 22, 22))

        presenter.handleResetButtonClick()

        verify(view).clearRows()
        verify(view).setLapVisibility(GONE)
        verify(view).updateTimer("00:00")
        verify(view).setStartButtonVisibility(VISIBLE)
        assertTrue(presenter.currSeconds == null)
    }

    @Test
    fun onSaveInstanceState() {
        val row = LapRow(0, 80, 80)
        presenter.currSeconds = 80
        presenter.laps.add(row)

        val bundle = mock(Bundle::class.java)
        presenter.onSaveInstanceState(mock(Parcelable :: class.java), bundle)
        verify(bundle).putInt("seconds", 80)
        verify(bundle).putParcelableArrayList("laps", presenter.laps)
    }

    @Test
    fun onRestoreInstanceState() {
        val laps = arrayListOf(LapRow(0, 15, 15))
        val bundle = mock(Bundle::class.java)
        Mockito.`when`(bundle.getInt("seconds", -1)).thenReturn(22)
        Mockito.`when`(bundle.getParcelableArrayList<LapRow>("laps")).thenReturn(laps)

        presenter.onRestoreInstanceState(bundle)

        assertEquals(22, presenter.currSeconds)
        assertEquals(laps, presenter.laps)
        verify(view).setLapVisibility(VISIBLE)
        verify(view).displayRow(laps[0].toString())
        verify(view).setStartButtonVisibility(GONE)
        verify(view, atLeast(1)).updateTimer("00:22")
    }

}