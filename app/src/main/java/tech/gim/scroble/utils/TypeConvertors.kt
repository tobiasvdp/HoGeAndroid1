package tech.gim.scroble.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import tech.gim.scroble.model.ShowImages
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC

class TypeConvertors {

    @TypeConverter
    fun listToString(value: List<String>?) = value?.joinToString(";")

    @TypeConverter
    fun stringToList(value: String?) = value?.split(";")

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun LocalDateTimeToLong(value: LocalDateTime) = value.toEpochSecond(UTC)

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun LongToLocalDateTime(value: Long) = LocalDateTime.ofEpochSecond(value, 0, UTC)

    @TypeConverter
    fun showImagesToEntityData(value: ShowImages): String = objectMapper.writeValueAsString(value)

    @TypeConverter
    fun showImagesFromEntityData(value: String): ShowImages = objectMapper.readValue<ShowImages>(value)

    companion object {
        var objectMapper = ObjectMapper().registerModule(KotlinModule())
    }
}
