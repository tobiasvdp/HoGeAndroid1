package tech.gim.scroble.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.gim.scroble.model.entity.*
import tech.gim.scroble.utils.TypeConvertors

@Database(entities = [DetailedShow::class, TrendingShow::class, MostAnticipatedShow::class, MostRecomendedShow::class, MostViewedShow::class, PopularShow::class, ShowImages::class, Season::class, Episode::class, DetailedEpisode::class, SearchedShow::class], version = 7, exportSchema = true)
@TypeConverters(TypeConvertors::class)
abstract class RoomsDatabase : RoomDatabase() {
    abstract fun showDatabaseDAO(): ShowDatabaseDAO
    abstract fun imagesDatabaseDAO(): ImagesDatabaseDAO
}
