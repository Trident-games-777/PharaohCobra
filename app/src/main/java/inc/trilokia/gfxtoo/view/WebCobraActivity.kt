package inc.trilokia.gfxtoo.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import inc.trilokia.gfxtoo.R
import inc.trilokia.gfxtoo.app.CobraApp
import inc.trilokia.gfxtoo.data.CobraRepository
import javax.inject.Inject

class WebCobraActivity : AppCompatActivity() {
    lateinit var webView: WebView
    var messageAb: ValueCallback<Array<Uri?>>? = null
    private val resultCode = 1

    @Inject
    lateinit var repository: CobraRepository

    private val imageTitle = "Image Chooser"
    private val image1 = "image/*"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_cobra)

        (applicationContext as CobraApp).appComponent.inject(this)
        webView = findViewById(R.id.wV)
        webView.loadUrl(intent.getStringExtra("currentLink")!!)
        webView.webViewClient = LocalClient()
        webView.settings.javaScriptEnabled = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = false

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            //For Android API >= 21 (5.0 OS)
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                messageAb = filePathCallback
                selectImageIfNeed()
                return true
            }

            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(applicationContext)
                newWebView.settings.javaScriptEnabled = true
                newWebView.webChromeClient = this
                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                newWebView.settings.domStorageEnabled = true
                newWebView.settings.setSupportMultipleWindows(true)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    private fun selectImageIfNeed() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = image1
        startActivityForResult(
            Intent.createChooser(i, imageTitle),
            resultCode
        )
    }

    private inner class LocalClient : WebViewClient() {

        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            if (errorCode == -2) {
                Toast.makeText(this@WebCobraActivity, "Error", Toast.LENGTH_LONG).show()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val currentLink = pref.getString("link", "domeliserred.xyz/coobra.php")
            if (currentLink!!.contains("domeliserred.xyz")) {
                with(pref.edit()) {
                    putString("link", url!!)
                    apply()
                }
            }
        }
    }
}