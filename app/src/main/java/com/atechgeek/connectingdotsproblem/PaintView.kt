package com.atechgeek.connectingdotsproblem

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast

/*
* Anshul Thakur on 23 April 2020
* */

class PaintView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet),
    OnTouchListener {
    private var mDotsPaint: Paint
    private var mLinePaint: Paint
    private val dotsList by lazy { ArrayList<Point>() }
    private var startPoint: Point? = null
    private var isTouchEnabled: Boolean = true

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        var j: Int = 0
        if (dotsList.isNotEmpty()) {
            startPoint = dotsList[j]
            canvas.drawCircle(startPoint!!.x.toFloat(), startPoint!!.y.toFloat(), 20f, mDotsPaint)
        }

        for (i in 1..dotsList.size - 1) {
            canvas.drawCircle(dotsList[i].x.toFloat(), dotsList[i].y.toFloat(), 20f, mDotsPaint)
            val previousPoint = dotsList[j]
            mLinePaint.setColor(getRandomColor())
            canvas.drawLine(
                previousPoint.x.toFloat(),
                previousPoint.y.toFloat(),
                dotsList[i].x.toFloat(),
                dotsList[i].y.toFloat(),
                mLinePaint
            )
            j++
        }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isTouchEnabled) {
                    return true
                }

                //Save the Point
                val p = Point(event.x.toInt(), event.y.toInt())
                if (isPointOverlap(startPoint, p)) {
                    isTouchEnabled = false
                    Toast.makeText(context, "All dots connected successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
                dotsList.add(p)
                invalidate()
            }
        }
        return true
    }

    private fun isPointOverlap(startPoint: Point?, p: Point): Boolean {
        if (startPoint == null) return false
        val intersect = checkCircleIntersection(startPoint.x, startPoint.y, p.x, p.y)
        return intersect >= 0
    }

    private fun checkCircleIntersection(
        x1: Int, y1: Int, x2: Int,
        y2: Int, r1: Int = 20, r2: Int = 20
    ): Int {
        val distSq = (x1 - x2) * (x1 - x2) +
                (y1 - y2) * (y1 - y2)
        val radSumSq = (r1 + r2) * (r1 + r2)
        return if (distSq == radSumSq) 1 else if (distSq > radSumSq) -1 else 0
    }

    private fun getRandomColor(): Int {
        return (Math.random() * 16777215).toInt() or (0xFF shl 24)
    }

    fun undo() {
        if (dotsList.isNotEmpty()) {
            dotsList.removeAt(dotsList.lastIndex)
            isTouchEnabled = true
            invalidate()
        }
    }

    fun clear() {
        dotsList.clear()
        startPoint = null
        isTouchEnabled = true
        invalidate()
    }


    init {
        mDotsPaint = Paint()
        mDotsPaint.setColor(Color.BLACK)

        mLinePaint = Paint()
        mLinePaint.strokeWidth = 8f
    }
}