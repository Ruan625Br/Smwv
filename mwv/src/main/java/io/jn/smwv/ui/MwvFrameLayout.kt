package io.jn.smwv.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class MwvFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var interceptTouchEvent = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return interceptTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (interceptTouchEvent) {
            return super.onTouchEvent(event)
        }
        return !interceptTouchEvent
    }
}