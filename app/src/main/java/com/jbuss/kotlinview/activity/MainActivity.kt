package com.jbuss.kotlinview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jbuss.kotlinview.R
import com.jbuss.kotlinview.view.TimerViewImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView.addView(TimerViewImpl(this))
    }
}
