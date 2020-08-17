package tech.gim.scroble

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import dagger.Component
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import tech.gim.scroble.api.FanartApi
import tech.gim.scroble.api.TraktApi
import tech.gim.scroble.dagger.ApplicationComponent
import tech.gim.scroble.dagger.ApplicationModule
import tech.gim.scroble.database.ImagesDatabaseDAO
import tech.gim.scroble.database.RoomsDatabase
import tech.gim.scroble.database.ShowDatabaseDAO
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.ReferenceData
import tech.gim.scroble.model.dto.DetailedShow
import tech.gim.scroble.model.dto.Show
import tech.gim.scroble.model.dto.TrendingShow
import tech.gim.scroble.service.ShowService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@RunWith(MockitoJUnitRunner::class)
class IntegrationTests {
    // @get:Rule val mockitoRule = MockitoJUnit.rule()
    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

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
    fun testMocking() {
        Mockito.`when`(showService.repository.traktApi.getDetailedShow(Mockito.anyString())).thenReturn(CompletableDeferred(DetailedShow("test")))
        val showDef = showService.repository.traktApi.getDetailedShow(1.toString())
        val show = showDef.getCompleted()
        assert(show.title.equals("test"))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testFeaturedShows() {
        val apiCall = CompletableDeferred<Array<TrendingShow>>()
        val databaseTrendingList = MutableLiveData<List<tech.gim.scroble.model.entity.TrendingShow>>()

        Mockito.`when`(showService.repository.traktApi.getTrendingShows(Mockito.any(), Mockito.any())).thenReturn(apiCall)
        Mockito.`when`(showService.repository.showDatabaseDAO.getTrendingShows()).thenReturn(databaseTrendingList)
        Mockito.`when`(showService.repository.showDatabaseDAO.insertAllTrendingShows(anyObject())).then(
            Answer { invocation ->
                val list = invocation.arguments[0] as List<tech.gim.scroble.model.entity.TrendingShow>
                databaseTrendingList.postValue(list)
            }
        )

        val livedata = showService.getTrendingShows()
        Assert.assertEquals(null, livedata.value)

        apiCall.complete(
            arrayOf(TrendingShow(Show("test", 1992, ReferenceData(1, "slug", 1, "1", 1, "1")), 10))
        )

        val data = livedata.getOrAwaitValue()
        Assert.assertEquals(1, data[0].traktId)
        Assert.assertEquals(10, data[0].watchers)
        Assert.assertEquals(MinimizedShow("test", 1992, ReferenceData(1, "slug", 1, "1", 1, "1")), data[0].show)
    }

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}

class TestModule(app: ScrobleApplication) : ApplicationModule(app) {
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
