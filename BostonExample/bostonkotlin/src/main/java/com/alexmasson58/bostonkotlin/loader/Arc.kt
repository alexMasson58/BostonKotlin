package com.alexmasson58.bostonkotlin.loader

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Created by frup66058 on 07/06/2018.
 */
class Arc(var startAngle: Float, var finalAngle: Float, var color: Int) {
    val rect = RectF()
    var radius = 150f

    fun setup(width: Int, height: Int, strokeWidth: Float = 0.0f) {
        radius = (minOf(width, height) / 2) - strokeWidth.toFloat()
        val centerX = width / 2f
        val centerY = height / 2f
        rect.set(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius)
    }


    fun onDraw(paint: Paint, canvas: Canvas) {
        paint.color = color //update current drawing color
        canvas.drawArc(rect, startAngle, finalAngle, false, paint)
    }
}