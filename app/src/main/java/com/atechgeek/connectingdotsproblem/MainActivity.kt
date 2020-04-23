package com.atechgeek.connectingdotsproblem

import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setListeners()
    }

    private fun setListeners() {
        dotsView.setOnTouchListener(dotsView)
        btnUndo.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnUndo -> {
                dotsView.undo()
            }
            btnReset -> {
                dotsView.clear()
            }
        }
    }
}
