package com.techdatum.epanchayat.textUi

import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.techdatum.epanchayat_application.R
import java.util.*


class AppCustomFontLoader {

    companion object {
        var types: HashMap<String, OverwatchTypeface> = HashMap()
        fun loadFont(view: View, attrs: AttributeSet) {
            var fontFamily: String? = null
            var fontFamilyType: String? = null
            var fontStyle = 0
            if (view is TextView) {
                if (view.typeface != null) {
                    fontStyle = view.typeface.style
                }
                val a = view.context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
            } else if (view is EditText) {

                if (view.typeface != null) {
                    fontStyle = view.typeface.style
                }
            }
            val a = view.context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
            val N = a.indexCount
            for (i in 0 until N) {
                val attr = a.getIndex(i)
                when (attr) {
                    R.styleable.CustomTextView_fontFamily -> fontFamily = a.getString(attr)
                    R.styleable.CustomTextView_fontFamilyType -> fontFamilyType = a.getString(attr)
                }
            }
            a.recycle()
            setTypeFace(view, fontFamily, fontFamilyType)
        }

        class OverwatchTypeface(var typeface: Typeface) {
            var style: Int = 0
        }

        private fun getTypefaceHashName(name: String?): String? {
            return name
        }

        fun setTypeFace(view: View, fontFamily: String?, fontFamilyType: String?) {
            val key = getTypefaceHashName(fontFamily)
            if (types.containsKey(key)) {
                val face = types[key]
                if (view is TextView) {
                    view.typeface = face!!.typeface
                } else if (view is EditText) {
                    view.typeface = face!!.typeface
                }

                return
            }
            //        if (fontFamilyType == null || fontFamilyType.trim().length() == 0) {
            //            fontFamilyType = "otf";
            //        }
            var type = Typeface.create(null as String?, Typeface.NORMAL)
            type = Typeface.createFromAsset(view.context.assets, "$fontFamily.$fontFamilyType")
            types[key!!] = OverwatchTypeface(type)
            if (view is TextView) {
                view.typeface = type
            } else if (view is EditText) {
                view.typeface = type
            }
        }
    }

}
