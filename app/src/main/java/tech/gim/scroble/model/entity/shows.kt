package tech.gim.scroble.model.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import tech.gim.scroble.model.ReferenceData
import java.time.LocalDateTime
import kotlin.collections.ArrayList

@Parcelize
@Entity(tableName = "SHOWS", indices = [Index(value = ["TRAKT_ID", "TITLE"])])
class DetailedShow : Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "TITLE")
    var title: String = ""
    @ColumnInfo(name = "YEAR")
    var year: Int = -1
    @Embedded
    var ids: ReferenceData = ReferenceData()
    @ColumnInfo(name = "OVERVIEW")
    var overview: String = ""
    @ColumnInfo(name = "FIRST_AIRED")
    var firstAired: LocalDateTime? = null
    @Embedded
    var airs: AirTime = AirTime()
    @ColumnInfo(name = "RUNTIME")
    var runtime: Int = -1
    @ColumnInfo(name = "CERTIFICATION")
    var certification: String? = null
    @ColumnInfo(name = "NETWORK")
    var network: String? = null
    @ColumnInfo(name = "COUNTRY")
    var country: String? = null
    @ColumnInfo(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null
    @ColumnInfo(name = "TRAILER")
    var trailer: String? = null
    @ColumnInfo(name = "HOMEPAGE")
    var homepage: String? = null
    @ColumnInfo(name = "STATUS")
    var status: String = ""
    @ColumnInfo(name = "RATING")
    var rating: Int? = null
    @ColumnInfo(name = "VOTES")
    var votes: Int? = null
    @ColumnInfo(name = "COMMENT_COUNT")
    var commentCount: Int? = null
    @ColumnInfo(name = "LANG")
    var language: String? = null
    @ColumnInfo(name = "TRANSLATIONS")
    var availableTranslations: List<String>? = ArrayList()
    @ColumnInfo(name = "GENRES")
    var genres: List<String>? = ArrayList()
    @ColumnInfo(name = "AIRED_EPISODES")
    var airedEpisodes: Int? = null
}

data class AirTime(@ColumnInfo(name = "AIR_DAY") var day: String? = "", @ColumnInfo(name = "AIR_TIME") var time: String? = "", @ColumnInfo(name = "AIR_TIMEZONE") var timezone: String? = "")

data class MinimizedShow(@ColumnInfo(name = "TITLE") var title: String? = "", @ColumnInfo(name = "YEAR") var year: Int? = -1, @Embedded var ids: ReferenceData? = ReferenceData())

@Entity(tableName = "TRENDING_SHOWS")
class TrendingShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "WATCHERS")
    var watchers: Int = -1
    @Embedded
    var serie: MinimizedShow? = null
}

@Entity(tableName = "SEARCHED_SHOWS")
class SearchedShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "SCORE")
    var score: Int? = -1
    @Embedded
    var serie: MinimizedShow? = null
}

@Entity(tableName = "MOST_VIEWED_SHOWS")
class MostViewedShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "WATCHER_COUNT")
    var watcher_count: Int = -1
    @ColumnInfo(name = "PLAY_COUNT")
    var play_count: Int = -1
    @ColumnInfo(name = "COLLECTED_COUNT")
    var collected_count: Int = -1
    @ColumnInfo(name = "COLLECTOR_COUNT")
    var collector_count: Int = -1
    @Embedded
    var serie: MinimizedShow? = null
}

@Entity(tableName = "MOST_RECOMENDED_SHOWS")
class MostRecomendedShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "USER_COUNT")
    var user_count: Int = -1
    @Embedded
    var serie: MinimizedShow? = null
}

@Entity(tableName = "MOST_ANTICIPATED_SHOWS")
class MostAnticipatedShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "LIST_COUNT")
    var list_count: Int = -1
    @Embedded
    var serie: MinimizedShow? = null
}
@Entity(tableName = "POPULAR_SHOWS")
class PopularShow() {
    @PrimaryKey
    @ColumnInfo(name = "TRAKT_ID")
    var traktId: Int = -1
    @ColumnInfo(name = "PLACE")
    var place: Int = -1
    @Embedded
    var serie: MinimizedShow? = null
}

@Entity(tableName = "SEASONS", indices = [Index("SHOW_ID")])
class Season() {
    @PrimaryKey
    @ColumnInfo(name = "CONCAT_ID")
    var id: String = ""
    @ColumnInfo(name = "SHOW_ID")
    var showId: Int = -1
    @ColumnInfo(name = "NUMBER")
    var number: Int = -1
    @Embedded
    var ids: ReferenceData? = null
}

@Entity(tableName = "EPISODES", indices = [Index("SEASON", "SHOW_ID")])
class Episode() {
    @PrimaryKey
    @ColumnInfo(name = "CONCAT_ID")
    var id: String = ""
    @ColumnInfo(name = "SHOW_ID")
    var showId: Int = -1
    @ColumnInfo(name = "SEASON")
    var season: Int = -1
    @ColumnInfo(name = "NUMBER")
    var number: Int = -1
    @ColumnInfo(name = "TITLE")
    var title: String = ""
    @Embedded
    var ids: ReferenceData? = null
}

@Entity(tableName = "EPISODES_DETAILS", indices = [Index("SHOW_ID")])
class DetailedEpisode() {
    @PrimaryKey
    @ColumnInfo(name = "CONCAT_ID")
    var id: String = ""
    @ColumnInfo(name = "SEASON")
    var season: Int = -1
    @ColumnInfo(name = "NUMBER")
    var number: Int = -1
    @ColumnInfo(name = "SHOW_ID")
    var showId: Int = -1
    @ColumnInfo(name = "TITLE")
    var title: String = ""
    @Embedded
    var ids: ReferenceData? = null
    @ColumnInfo(name = "NUMBER_ABS")
    var numberAbs: Int? = null
    @ColumnInfo(name = "OVERVIEW")
    var overview: String? = null
    @ColumnInfo(name = "FIRST_AIRED")
    var firstAired: LocalDateTime? = null
    @ColumnInfo(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null
    @ColumnInfo(name = "RATING")
    var rating: Int? = -1
    @ColumnInfo(name = "VOTES")
    var votes: Int? = -1
    @ColumnInfo(name = "COMMENT_COUNT")
    var commentCount: Int? = -1
    @ColumnInfo(name = "AVAILABLE_TRANSLATIONS")
    var availableTranslations: List<String>? = ArrayList()
    @ColumnInfo(name = "RUNTIME")
    var runtime: Int? = -1
}
