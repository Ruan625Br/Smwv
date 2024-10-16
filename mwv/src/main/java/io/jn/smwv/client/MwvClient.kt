package io.jn.smwv.client

import android.graphics.Color
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import io.jn.smwv.web.MwvHandle

class MwvClient(
    private val mwvHandle: MwvHandle
) : WebViewClient() {


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.setBackgroundColor(Color.TRANSPARENT)
        view?.visibility = View.VISIBLE
        mwvHandle.onBrowserInit(true, -1)
    }


    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (request?.isForMainFrame == true) {
            mwvHandle.onBrowserInit(false, errorResponse?.statusCode ?: 0)
            Log.e("MwvClient", "onReceivedHttpError: ${errorResponse?.statusCode}")
        }
    }
}
