package com.jbuss.kotlinview.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.jbuss.kotlinview.R
import com.jbuss.kotlinview.model.SecondsTimer
import com.jbuss.kotlinview.presenter.TimerPresenterImpl
import kotlinx.android.synthetic.main.timer_view.view.*

/**
 * Created by jbuss on 2/22/18.
 */
class TimerViewImpl(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr), TimerView {
    private val presenter: TimerPresenterImpl

    init {
        LayoutInflater.from(context).inflate(R.layout.timer_view, this, true)

        presenter = TimerPresenterImpl(this, SecondsTimer())

        startTimerButton.setOnClickListener({ presenter.handleStartTimerClick() })
        lapButton.setOnClickListener({ presenter.handleLapButtonClick() })
        resetButton.setOnClickListener({ presenter.handleResetButtonClick() })

        // needed for handling rotations, apparently
        id = R.id.timer_view_id
    }

    override fun setStartButtonVisibility(visibility: Int) {
        startTimerButton.visibility = visibility
    }

    override fun setLapVisibility(visibility: Int) {
        lapCardView.visibility = visibility
    }

    override fun updateTimer(textToDisplay: String) {
        Handler(Looper.getMainLooper()).post( { timerDisplay.text = textToDisplay })
    }

    override fun displayRow(textToDisplay: String) {
        val textView = TextView(context)
        textView.text = textToDisplay

        lapContainer.addView(textView)
    }

    override fun clearRows() {
        lapContainer.removeAllViews()
    }

    override fun onSaveInstanceState(): Parcelable {
        return presenter.onSaveInstanceState(super.onSaveInstanceState(), Bundle())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState((state as Bundle).getParcelable("super"))
        presenter.onRestoreInstanceState(state)
    }
}