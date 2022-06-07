package inc.trilokia.gfxtoo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onesignal.OneSignal
import inc.trilokia.gfxtoo.data.CobraRepository
import inc.trilokia.gfxtoo.utils.LinkParser
import kotlinx.coroutines.async

class CobraViewModel(
    private val repository: CobraRepository,
    private val parser: LinkParser
) : ViewModel() {

    fun getAppsKeyAsync() = viewModelScope.async {
        repository.getSnapshot().child("apps_key").value as String
    }

    fun getOneKeyAsync() = viewModelScope.async {
        repository.getSnapshot().child("one_key").value as String
    }

    fun processLink(
        data: MutableMap<String, Any>?,
        deep: String,
        baseLink: String,
        gadid: String
    ): String {
        val link = parser.parse(data, deep, baseLink, gadid)

        when {
            data?.get("campaign").toString() == "null" && deep == "null" -> {
                OneSignal.sendTag(
                    "key2",
                    "organic"
                )
            }
            deep != "null" && data?.get("campaign").toString() == "null" -> {
                OneSignal.sendTag(
                    "key2",
                    deep.replace("myapp://", "").substringBefore("/")
                )
            }
            data?.get("campaign").toString() != "null" && deep == "null" -> {
                OneSignal.sendTag(
                    "key2",
                    data?.get("campaign").toString().substringBefore("_")
                )
            }
        }
        return link
    }
}