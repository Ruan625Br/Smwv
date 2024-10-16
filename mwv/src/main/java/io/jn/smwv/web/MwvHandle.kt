package io.jn.smwv.web

open class MwvHandle {

    external fun sendEvent(name: String, data: String)

    external fun onBrowserInit(init: Boolean, httpErrorCode: Int)
}