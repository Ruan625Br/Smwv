package io.jn.smwv

import android.app.Activity
import android.widget.FrameLayout
import io.jn.smwv.ui.WebViewManager

class MwvManager(
   frameLayout: FrameLayout,
   activity: Activity
) : WebViewManager(frameLayout, activity) {

   external fun setup()


}