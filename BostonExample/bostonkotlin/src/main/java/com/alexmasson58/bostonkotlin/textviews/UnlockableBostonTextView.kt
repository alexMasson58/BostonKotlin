package com.alexmasson58.bostonkotlin.textviews

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet

/**
 * Created by frup66058 on 03/07/2018.
 */
class UnlockableBostonTextView(context: Context, attrs: AttributeSet) : BostonTextView(context, attrs) {

    private val triangleColor = "#11FFFFFF"
    private var locked = true

    private var offset = 10f
    private val rect = Rect()
    var radius = 150f


    private val backgroundPaint = Paint().apply {
        strokeWidth = 10f
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val trianglePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor(triangleColor)
        isAntiAlias = true
    }


    private val bracketPaint = Paint().apply {
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var wdth: Float = 0f

    private var hgth: Float = 0f

    val textPaint = TextPaint().apply {
        color = Color.parseColor("#FFFFFF")
        typeface = MyTypeface!!
        textSize = 36f
        isAntiAlias = true


    }

    val textLockPaint = TextPaint().apply {
        color = Color.parseColor("#77333333")
        typeface = MyTypeface!!
        textSize = 36f
        isAntiAlias = true
    }

    private lateinit var lockedLayout: StaticLayout

    private lateinit var unlockedLayout: StaticLayout

    fun switchLock() {
        locked = !locked
        invalidate()
    }

    fun unlock() {
        locked = false
        invalidate()
    }

    fun lock() {
        locked = true
        invalidate()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        wdth = w.toFloat()
        hgth = h.toFloat()
        rect.top = offset.toInt()
        rect.left = offset.toInt()
        rect.right = (wdth - offset).toInt()
        rect.bottom = (hgth - offset).toInt()


        var textWidth = Math.min(width, textPaint.measureText(text.toString().toUpperCase()).toInt())

        if (text.length == 0) {
            textWidth = (wdth - (2 * offset)).toInt()
        }
//warning : allocation ondraw
        lockedLayout = StaticLayout(lockedString, textLockPaint, textWidth,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false)

        unlockedLayout = StaticLayout(text.toString().toUpperCase(), textPaint, textWidth,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false)

        /*  var mediane = hgth - (2 * offset)
          var triangleSide = mediane / (Math.sqrt(3.0)/2).toFloat()*/


    }

    override fun onDraw(canvas: Canvas) {
        /* super.onDraw(canvas)*/
        val color = Color.parseColor(when (locked) {
            true -> "#55333333" // grey light
            false -> "#990033FF" // blue light
        }) //update current drawing color

        listOf(backgroundPaint, bracketPaint).forEach {
            it.color = color
        }

        canvas.drawRect(rect, backgroundPaint)
        drawPetitCote(canvas)
        drawPremierTriangle(canvas)
        drawPremierTriangle(canvas)
        drawPremierTriangle(canvas)
        drawSecondTriangle(canvas)
        drawSecondTriangle(canvas)
        drawTroisiemeTriangle(canvas)
        if (showbrackets) {
            canvas.drawLine(0f, 0f, offset, 0f, bracketPaint)
            canvas.drawLine(0f, 0f, 0f, offset, bracketPaint)

            canvas.drawLine(0f, hgth, 0f, hgth - offset, bracketPaint)
            canvas.drawLine(0f, hgth, offset, hgth, bracketPaint)

            canvas.drawLine(wdth, 0f, wdth - offset, 0f, bracketPaint)
            canvas.drawLine(wdth, 0f, wdth, offset, bracketPaint)

            canvas.drawLine(wdth, hgth, wdth - offset, hgth, bracketPaint)
            canvas.drawLine(wdth, hgth, wdth, hgth - offset, bracketPaint)
        }
        if (showlock) {
            //handle drawing lock icon
        }
        canvas.translate(offset*2, ((canvas.getHeight() / 2) - ((textPaint.descent() - textPaint.ascent()) / 2)));

//After that to reset the canvas back for everything else
        if (locked) {


            lockedLayout.draw(canvas)
        } else {

            unlockedLayout.draw(canvas)
        }
        canvas.restore();


    }

    fun drawPetitCote(canvas: Canvas) {

        //thales ->
        val petitcotehauteur = (hgth * (hgth / 3)) / (hgth / 2)
        val path = Path()
        path.reset()
        path.moveTo((wdth - offset) - hgth / 3, offset)
        path.lineTo((wdth - offset), offset + petitcotehauteur)
        path.lineTo((wdth - offset), offset)
        path.lineTo((wdth - offset) - hgth / 3, offset)
        path.close()
        canvas.drawPath(path, trianglePaint)
    }

    fun drawPremierTriangle(canvas: Canvas) {

        val path = Path()
        path.reset()
        path.moveTo((wdth - offset) - hgth / 3, offset)
        path.lineTo(((wdth - offset) - hgth / 3) - hgth / 2, hgth - offset)
        path.lineTo(((wdth - offset) - hgth / 3) - hgth, offset)
        path.lineTo((wdth - offset) - hgth / 3, offset)
        path.close()
        canvas.drawPath(path, trianglePaint)
    }

    fun drawSecondTriangle(canvas: Canvas) {
        val path = Path()
        path.reset()
        path.moveTo(((wdth - offset) - hgth / 3) - hgth, offset)
        path.lineTo(((wdth - offset) - hgth / 3) - hgth / 2, hgth - offset)
        path.lineTo((((wdth - offset) - hgth / 3) - hgth / 2) - hgth, hgth - offset)
        path.lineTo(((wdth - offset) - hgth / 3) - hgth, offset)
        path.close()
        canvas.drawPath(path, trianglePaint)

    }

    fun drawTroisiemeTriangle(canvas: Canvas) {
        val path = Path()
        path.reset()
        path.moveTo((((wdth - offset) - hgth / 3) - hgth / 2) - hgth, hgth - offset)
        path.lineTo(((wdth - offset) - hgth / 3) - hgth, offset)
        path.lineTo((((wdth - offset) - hgth / 3) - hgth) - hgth, offset)
        path.lineTo((((wdth - offset) - hgth / 3) - hgth / 2) - hgth, hgth - offset)

        path.close()
        canvas.drawPath(path, trianglePaint)

    }


}