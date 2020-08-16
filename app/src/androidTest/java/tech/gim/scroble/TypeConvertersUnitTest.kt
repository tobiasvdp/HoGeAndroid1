package tech.gim.scroble

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import androidx.test.core.app.ApplicationProvider
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import tech.gim.scroble.model.Image
import tech.gim.scroble.model.ShowImages
import tech.gim.scroble.utils.TypeConvertors
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TypeConvertersUnitTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val convertors = TypeConvertors()

    @Test
    fun listToString(){
        val result = convertors.listToString(arrayListOf("test1", "number2"))
        assert(result.equals("test1;number2"))
    }

    @Test
    fun stringToList(){
        val result = convertors.stringToList("test1;number2")
        assert(result?.get(0).equals("test1") && result?.get(1).equals("number2") )
    }

    @Test
    fun LocalDateTimeToLong() {
        val result = convertors.LocalDateTimeToLong(LocalDateTime.parse("1992-10-16T04:10", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
        assert(result == 719208600L)
    }

    @Test
    fun LongToLocalDateTime() {
        val result = convertors.LongToLocalDateTime(719208600L)
        assert(result.year == 1992 && result.month == Month.OCTOBER && result.dayOfMonth == 16 && result.hour == 4 && result.minute == 10)
    }

    @ExperimentalStdlibApi
    @Test
    fun showImagesToEntityData() {
        val result = convertors.showImagesToEntityData(ShowImages(traktId = 1, banner = Image("banner"), poster = Image("poster"), seasonPosters = buildMap {
            this.put(0, Image("0"))
            this.put(1, Image("1"))
        }))
        assert(result.equals("{\"traktId\":1,\"poster\":{\"src\":\"poster\"},\"seasonPosters\":{\"0\":{\"src\":\"0\"},\"1\":{\"src\":\"1\"}},\"banner\":{\"src\":\"banner\"},\"seasonBanners\":{}}"))
    }

    @Test
    fun showImagesFromEntityData(){
        val result = convertors.showImagesFromEntityData("{\"traktId\":1,\"poster\":{\"src\":\"poster\"},\"seasonPosters\":{\"0\":{\"src\":\"0\"},\"1\":{\"src\":\"1\"}},\"banner\":{\"src\":\"banner\"},\"seasonBanners\":{}}")
        assert(result.traktId == 1 && result.poster?.src.equals("poster") && result.seasonPosters.get(0)?.src.equals("0"))
    }
}