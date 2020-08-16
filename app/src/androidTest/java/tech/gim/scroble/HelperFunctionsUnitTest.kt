package tech.gim.scroble

import android.content.Context
import android.util.DisplayMetrics
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import tech.gim.scroble.model.*
import tech.gim.scroble.utils.HelperFunctions

class HelperFunctionsUnitTest {
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testGetCardWidth(){
        val width = HelperFunctions.getCardWidth(context)
        assert(width == 114.0f)
    }

    @Test
    fun testCalculateNoOfColumns(){
        val number = HelperFunctions.calculateNoOfColumns(context, HelperFunctions.getCardWidth(context))
        assert(number == 3)
    }

    @Test
    fun testCombineShowsAndImages(){
        val shows = arrayListOf(MinimizedShow(title = "1", ids = ReferenceData(trakt = 1)), MinimizedShow(title = "10", ids = ReferenceData(trakt = 10)), MinimizedShow(title = "5", ids = ReferenceData(trakt = 5)))
        val images = arrayListOf(ShowImages(traktId = 1, poster = Image("1")), ShowImages(traktId = 2, poster = Image("2")), ShowImages(traktId = 10, poster = Image("10")))
        val result = HelperFunctions.combineShowsAndImages(shows, images)
        assert(result.size == 3)
        assert(result[0].images != null && result[0].images?.traktId == 1)
        assert(result[1].images != null && result[1].images?.traktId == 10)
        assert(result[2].images == null)
    }
}