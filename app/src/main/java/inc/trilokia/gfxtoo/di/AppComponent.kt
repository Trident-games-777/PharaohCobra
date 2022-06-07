package inc.trilokia.gfxtoo.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import inc.trilokia.gfxtoo.view.LoadCobraActivity
import inc.trilokia.gfxtoo.view.WebCobraActivity

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(loadCobraActivity: LoadCobraActivity)
    fun inject(webCobraActivity: WebCobraActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}