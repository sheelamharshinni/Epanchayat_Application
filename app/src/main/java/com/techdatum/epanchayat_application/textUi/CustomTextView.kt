package com.techdatum.epanchayat.textUi

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")
class CustomTextView : TextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        AppCustomFontLoader.loadFont(this, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        AppCustomFontLoader.loadFont(this, attrs)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        var c = text
        super.setText(c, type)
    }

}
