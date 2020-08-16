package tech.gim.scroble

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.Component
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import tech.gim.scroble.api.FanartApi
import tech.gim.scroble.api.TraktApi
import tech.gim.scroble.dagger.ApplicationComponent
import tech.gim.scroble.dagger.ApplicationModule
import tech.gim.scroble.database.ImagesDatabaseDAO
import tech.gim.scroble.database.RoomsDatabase
import tech.gim.scroble.database.ShowDatabaseDAO
import tech.gim.scroble.model.dto.DetailedShow
import tech.gim.scroble.service.ShowService
import javax.inject.Inject
import javax.inject.Singleton


@RunWith(MockitoJUnitRunner::class)
class IntegrationTests {
    //@get:Rule val mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var traktApi: TraktApi

    @Mock
    lateinit var fanartApi: FanartApi

    @Mock
    lateinit var roomsDatabase: RoomsDatabase

    @Mock
    lateinit var showDao: ShowDatabaseDAO

    @Mock
    lateinit var imagesDao: ImagesDatabaseDAO

    @Inject
    lateinit var showService: ShowService

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<Context>() as ScrobleApplication
        val component: TestComponent = DaggerTestComponent.builder()
            .applicationModule(TestModule(app)).build()
        app.component = component
        component?.inject(this)
    }
    @Test
    fun testDagger() {
        assert(showService.repository.traktApi != null)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testAPi() {
        Mockito.`when`(showService.repository.traktApi.getDetailedShow(Mockito.anyString())).thenReturn(CompletableDeferred(DetailedShow("test")))
        val showDef = showService.repository.traktApi.getDetailedShow(1.toString())
        val show = showDef.getCompleted()
        assert(show.title.equals("test"))
    }

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

}

class TestModule(app: ScrobleApplication): ApplicationModule(app) {
    override fun provideTraktApi(): TraktApi {
        return Mockito.mock(TraktApi::class.java)
    }

    override fun provideFanartApi(): FanartApi {
        return Mockito.mock(FanartApi::class.java)
    }

    override fun provideRoomsDatabase(app: ScrobleApplication): RoomsDatabase {
        return Mockito.mock(RoomsDatabase::class.java)
    }

    override fun provideShowDatabaseDAO(roomsDatabase: RoomsDatabase): ShowDatabaseDAO {
        return Mockito.mock(ShowDatabaseDAO::class.java)
    }

    override fun provideImagesDatabaseDAO(roomsDatabase: RoomsDatabase): ImagesDatabaseDAO {
        return Mockito.mock(ImagesDatabaseDAO::class.java)
    }
}

@Singleton
@Component(modules = [ApplicationModule::class])
interface TestComponent : ApplicationComponent {
    fun inject(integrationTests: IntegrationTests)
}