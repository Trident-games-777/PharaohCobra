package inc.trilokia.gfxtoo.di

import android.content.Context
import android.content.res.Resources
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import inc.trilokia.gfxtoo.data.CobraRepository
import inc.trilokia.gfxtoo.utils.LinkParser
import inc.trilokia.gfxtoo.view_model.CobraViewModelFactory

private const val DATABASE_URL =
    "https://pharaohcobra-6a0e9-default-rtdb.europe-west1.firebasedatabase.app/"

@Module
class AppModule {
    @Provides
    fun provideFirebaseDatabase(): DatabaseReference {
        return Firebase
            .database(DATABASE_URL)
            .reference
    }

    @Provides
    fun provideCobraViewModelFactory(
        repository: CobraRepository,
        parser: LinkParser
    ): CobraViewModelFactory {
        return CobraViewModelFactory(repository, parser)
    }

    @Provides
    fun provideCobraRepository(database: DatabaseReference): CobraRepository {
        return CobraRepository(database)
    }

    @Provides
    fun provideLinkParser(
        context: Context,
    ): LinkParser {
        return LinkParser(context)
    }

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }
}