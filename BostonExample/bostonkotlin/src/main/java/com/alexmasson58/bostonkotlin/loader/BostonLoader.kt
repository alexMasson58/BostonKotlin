package com.alexmasson58.bostonkotlin.loader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.security.InvalidParameterException


class BostonLoader(context: Context, attributes: AttributeSet) : View(context, attributes) {
    companion object {
        val duration = 7000L

    }

    private val paint = Paint().apply {
        strokeWidth = 10f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }
    private var offset = 10f
    private var normalColorString = "#330033FF"
    private var workingColorString = "#33FFF414"
    private var errorColorString = "#33FF0000"

    private var arc1 = Arc(240f, 360f, Color.parseColor(normalColorString))
    private var arc2 = Arc(120f, 230f, Color.parseColor(normalColorString))
    private var arc3 = Arc(0f, 110f, Color.parseColor(normalColorString))


    private val arcBackground = Arc(0f, 360f, Color.parseColor(normalColorString))


    private var drawBackgroundArc = false
    private var sep1 = 0.0f
    private var sep2 = 90.0f
    private var sep3 = 130.0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (drawBackgroundArc) {
            arcBackground.setup(w, h, paint.strokeWidth)
        }

        if ((minOf(w, h)) < 200) {
            offset = 20f
            if ((minOf(w, h)) < 100) {
                paint.strokeWidth = 6f
            }
        }
        arc1.setup(w, h, paint.strokeWidth)
        arc2.setup(w, h, paint.strokeWidth)
        arc3.setup(w, h, paint.strokeWidth)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (drawBackgroundArc) {
            arcBackground.onDraw(paint, canvas)
        }
        listOf(arc1, arc2, arc3).forEach { arc ->
            arc.onDraw(paint, canvas)
        }
    }


    fun animateMe() {

        ValueAnimator.ofFloat(0.0f, 2.0f, 0.0f)
                .apply {
                    repeatMode = ValueAnimator.RESTART
                    repeatCount = ValueAnimator.INFINITE
                    duration = BostonLoader.duration
                    addUpdateListener {
                        sep1 += animatedValue as Float
                        sep1 %= 360
                        sep2 += (animatedValue as Float / 2)
                        sep2 %= 360
                        sep3 += (animatedValue as Float / 3f)
                        sep3 %= 360
                        arc1.startAngle = sep1
                        arc1.finalAngle = getAngle(sep1, sep2, sep3)
                        arc2.startAngle = sep2
                        arc2.finalAngle = getAngle(sep2, sep3, sep1)
                        arc3.startAngle = sep3
                        arc3.finalAngle = getAngle(sep3, sep1, sep2)
                        invalidate()
                    }
                }.start()

    }


    private fun getAngle(pos1: Float, pos2: Float, pos3: Float): Float {

        if (pos1 < 0f || pos1 > 360f || pos2 < 0f || pos2 > 360f || pos3 < 0f || pos3 > 360f) {
            throw InvalidParameterException("angles are not correct")
        }
        //same pos
        var other1 = pos2
        var other2 = pos3
        val nextIsOther1: Boolean
        val other: Float
        if (pos1 == pos2 || pos1 == pos3) {
            return 0f // nothing to draw
        } else {
            //find nearest next pos
            //then calculate angle
            if (pos2 < pos1) {
                other1 += 360f
            }
            if (pos3 < pos1) {
                other2 += 360f
            }

            nextIsOther1 = other1 <= other2


            //here we found next point, other 1 or other2
            other = when (nextIsOther1) {
                true -> pos2
                false -> pos3
            }

            return if (pos1 < other) {
                maxOf((other - pos1) - offset, 0.0f)
            } else {
                ((360f - pos1) - offset) + other
            }
        }
    }

    private fun changeColor(colorString: String) {
        listOf(arc1, arc2, arc3).forEach {
            it.color = Color.parseColor(colorString)
        }
        invalidate()
    }

    fun setStandBy() {
        changeColor(normalColorString)
    }

    fun setWorking() {
        changeColor(workingColorString)
    }

    fun setError() {
        changeColor(errorColorString)
    }
}