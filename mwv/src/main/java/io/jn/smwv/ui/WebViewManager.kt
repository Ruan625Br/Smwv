package io.jn.smwv.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.webkit.WebView
import android.widget.FrameLayout
import io.jn.smwv.client.MwvClient
import io.jn.smwv.client.MwvInterface
import io.jn.smwv.web.MwvHandle
import io.jn.smwv.R

open class WebViewManager(
    private val frameLayout: FrameLayout, private val activity: Activity
) : MwvHandle() {
    private lateinit var webView: WebView
    private lateinit var mwvFrame: MwvFrameLayout
    private var isInitialized = false
    private lateinit var mwvInterface: MwvInterface
    private lateinit var mwvClient: MwvClient

    @SuppressLint("SetJavaScriptEnabled")
    private fun initialize() = activity.runOnUiThread {
        mwvClient = MwvClient(this)

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mwvFrame = MwvFrameLayout(activity)
        inflater.inflate(R.layout.mwv, mwvFrame, true)
        frameLayout.addView(
            mwvFrame,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mwvFrame.visibility = View.GONE
        webView = mwvFrame.findViewById(R.id.webview)
        mwvInterface = MwvInterface(this, webView, activity)
        webView.apply {
            webViewClient = mwvClient
            settings.javaScriptEnabled = true
            addJavascriptInterface(mwvInterface, MwvInterface.MWV)
        }

        isInitialized = true
    }

    private fun destroyView() = runSafe {
        (frameLayout.parent as ViewManager).removeView(mwvFrame)

    }

    fun destroy() = runSafe {
        hide()
        webView.clearHistory()
        webView.clearCache(true)
        webView.loadUrl("about:blank")
        webView.onPause()
        if (webView.parent != null) {
            (webView.parent as ViewGroup).removeView(webView)
        }
        webView.removeAllViews()
        webView.destroy()
        destroyView()
        isInitialized = false
    }

    fun show() = runSafe {
        mwvFrame.visibility = View.VISIBLE
        Log.d("WebViewManager", "show")
    }

    fun hide() = runSafe {
        mwvFrame.visibility = View.GONE
    }

    fun init(url: String) = activity.runOnUiThread {
        if (isInitialized) {
            destroy()
        }
        initialize()
        setBrowserUrl(url)
        hide()
    }

    fun setBrowserUrl(url: String) = runSafe {
        webView.loadUrl(url)
    }

    fun sendEventToWeb(name: String, data: String) = runSafe {
        mwvInterface.sendEventToWeb(name, data)
    }

    fun setBrowserFocus(focus: Boolean) = runSafe {
        mwvFrame.interceptTouchEvent = !focus
    }

    fun toggleVisibility() = runSafe {
        if (mwvFrame.visibility == View.VISIBLE) {
            hide()
        } else {
            show()
        }
    }

    fun reload() = runSafe {
        webView.reload()
    }

    private fun runSafe(block: () -> Unit) {
        activity.runOnUiThread {
            if (isInitialized) {
                block()
            }
        }
    }
}