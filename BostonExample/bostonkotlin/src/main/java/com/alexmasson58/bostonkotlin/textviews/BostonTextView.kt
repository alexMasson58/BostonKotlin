package com.alexmasson58.bostonkotlin.textviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.alexmasson58.bostonkotlin.R

/**
 * Created by frup66058 on 03/07/2018.
 */
abstract class BostonTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    protected val lockedString = "[...]"

    protected var showlock = true
    protected var showbrackets = true
    protected var MyTypeface: Typeface? = null


    init {
        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (!isInEditMode) {
            this.setAllCaps(true)
            val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.BostonTextView)
            showlock = styledAttrs.getBoolean(R.styleable.BostonTextView_boston_showlock, true)
            showbrackets = styledAttrs.getBoolean(R.styleable.BostonTextView_boston_showbrackets, true)
            styledAttrs.recycle()

            setTypeFace("Roboto-Light.ttf")
        }

    }


    fun setTypeFace(fontName: String?) {
        if (fontName != null) {
            try {
                MyTypeface = Typeface.createFromAsset(context.assets, "fonts/" + fontName)
                typeface = MyTypeface!!
            } catch (e: Exception) {
                Log.e("FONT", fontName + " not found", e)
            }

        }
    }
}
