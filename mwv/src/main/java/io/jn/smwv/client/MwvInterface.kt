package io.jn.smwv.client

import android.app.Activity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import io.jn.smwv.web.MwvHandle
import org.json.JSONObject

class MwvInterface(
    private val mwvHandle: MwvHandle,
    private val webView: WebView?,
    private val activity: Activity
) {
    private val eventCallbacks = mutableMapOf<String, String>()

    init {
        registerEventCallback(MWV_EVENT_NAME, MWV_EVENT_CALLBACK)
    }

    @JavascriptInterface
    fun sendEvent(name: String, data: String) {
        if (name.isNotEmpty() && data.isNotEmpty()) {
            mwvHandle.sendEvent(name, data)
        }
    }

    @JavascriptInterface
    fun registerEventCallback(eventName: String, callbackName: String) {
        if (eventName.isNotEmpty() && callbackName.isNotEmpty()) {
            eventCallbacks[eventName] = callbackName
        }

    }

    fun sendEventToWeb(name: String, data: String) = activity.runOnUiThread {
        val callbackName = eventCallbacks[name]
        val escapedData = JSONObject.quote(data)
        sendMwvEventIfAvailable(name, escapedData)

        if (name.isNotEmpty() && data.isNotEmpty() && callbackName != null && webView != null){
            val script = "$callbackName($escapedData);"
            webView.evaluateJavascript(script, null)
        }

    }

    private fun sendMwvEventIfAvailable(name: String, data: String) = activity.runOnUiThread {
        val callback = eventCallbacks[MWV_EVENT_NAME]
        val script = "$callback(\"$name\", $data);"
        webView!!.evaluateJavascript(script, null)

    }



    companion object {
        const val MWV_EVENT_NAME = "handle_mwv_event"
        const val MWV_EVENT_CALLBACK = "handleMwvEvent"
        const val MWV = "Mwv"
    }
}