package tech.gim.scroble.dagger

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import tech.gim.scroble.BuildConfig
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.api.FanartApi
import tech.gim.scroble.api.TraktApi
import tech.gim.scroble.database.RoomsDatabase
import tech.gim.scroble.repository.ImageRepository
import tech.gim.scroble.repository.ShowRepository
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.PreferenceService
import tech.gim.scroble.service.ShowService
import javax.inject.Singleton

@Module
class ApplicationModule(val app: ScrobleApplication) {
    @Provides @Singleton fun provideApp() = app
    @Provides @Singleton fun provideTraktApi() = Retrofit.Builder()
        .baseUrl(BuildConfig.TRAKT_API_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("trakt-api-version", "2")
                            .header("trakt-api-key", BuildConfig.TRAKT_CLIENT_ID)
                            .method(original.method(), original.body())
                            .build()
                        return@Interceptor chain.proceed(request)
                    }
                )
                .build()
        )
        .build()
        .create(TraktApi::class.java)
    @Provides @Singleton fun provideFanartApi() = Retrofit.Builder()
        .baseUrl(BuildConfig.FANART_API_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient.Builder()
                .build()
        )
        .build()
        .create(FanartApi::class.java)
    @Provides @Singleton fun provideRoomsDatabase(app: ScrobleApplication) = Room.databaseBuilder(app, RoomsDatabase::class.java, "trakt_client_database")
        .fallbackToDestructiveMigration()
        .build()
    @Provides fun provideShowDatabaseDAO(roomsDatabase: RoomsDatabase) = roomsDatabase.showDatabaseDAO()
    @Provides fun provideImagesDatabaseDAO(roomsDatabase: RoomsDatabase) = roomsDatabase.imagesDatabaseDAO()
    @Provides @Singleton fun provideTraktService(app: ScrobleApplication) = ShowService(app)
    @Provides @Singleton fun provideImageService(app: ScrobleApplication) = ImageService(app)
    @Provides @Singleton fun providePreferenceService(app: ScrobleApplication) = PreferenceService(app)
    @Provides @Singleton fun provideShowRepository(app: ScrobleApplication) = ShowRepository(app)
    @Provides @Singleton fun provideImagesRepository(app: ScrobleApplication) = ImageRepository(app)
}
