package inc.trilokia.gfxtoo.utils

import com.appsflyer.AppsFlyerConversionListener

class AppsWrapper(private val callback: (MutableMap<String, Any>?) -> Unit) :
    AppsFlyerConversionListener {
    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
        callback(data)
    }

    override fun onConversionDataFail(p0: String?) {}
    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
    override fun onAttributionFailure(p0: String?) {}
}