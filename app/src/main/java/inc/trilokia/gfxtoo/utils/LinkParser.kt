package inc.trilokia.gfxtoo.utils

import android.content.Context
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import inc.trilokia.gfxtoo.R
import java.util.*

private fun LinkParser.string(idRes: Int): String = context.resources.getString(idRes)

class LinkParser(
    val context: Context,
) {
    fun parse(
        data: MutableMap<String, Any>?,
        deep: String,
        currentLink: String,
        gadid: String
    ): String = currentLink.toUri().buildUpon().apply {
        appendQueryParameter(string(R.string.secure_get_parametr), string(R.string.secure_key))
        appendQueryParameter(string(R.string.dev_tmz_key), TimeZone.getDefault().id)
        appendQueryParameter(string(R.string.gadid_key), gadid)
        appendQueryParameter(string(R.string.deeplink_key), deep)
        appendQueryParameter(string(R.string.source_key), data?.get("media_source").toString())
        appendQueryParameter(
            string(R.string.af_id_key),
            AppsFlyerLib.getInstance().getAppsFlyerUID(context)
        )
        appendQueryParameter(string(R.string.adset_id_key), data?.get("adset_id").toString())
        appendQueryParameter(
            string(R.string.campaign_id_key),
            data?.get("campaign_id").toString()
        )
        appendQueryParameter(
            string(R.string.app_campaign_key),
            data?.get("campaign").toString()
        )
        appendQueryParameter(string(R.string.adset_key), data?.get("adset").toString())
        appendQueryParameter(string(R.string.adgroup_key), data?.get("adgroup").toString())
        appendQueryParameter(string(R.string.orig_cost_key), data?.get("orig_cost").toString())
        appendQueryParameter(string(R.string.af_siteid_key), data?.get("af_siteid").toString())
    }.toString()
}