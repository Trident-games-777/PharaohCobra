package inc.trilokia.gfxtoo.app

import android.app.Application
import inc.trilokia.gfxtoo.di.AppComponent
import inc.trilokia.gfxtoo.di.DaggerAppComponent

class CobraApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}