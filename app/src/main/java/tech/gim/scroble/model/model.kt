package tech.gim.scroble.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Parcelize
data class ReferenceData(var trakt: Int? = -1, var slug: String? = "", var tvdb: Int? = -1, var imdb: String? = "", var tmdb: Int? = -1, var tvrage: String? = "") : Parcelable
data class AirTime(var day: String? = "", var time: String? = "", var timezone: String? = "")
@Parcelize
data class MinimizedShow(var title: String? = "", var year: Int? = -1, var ids: ReferenceData? = ReferenceData()) : Parcelable
data class TrendingShow(var traktId: Int = -1, var watchers: Int = -1, var show: MinimizedShow? = null)
data class MostViewedShow(var traktId: Int = -1, var watcher_count: Int = -1, var play_count: Int = -1, var collected_count: Int = -1, var collector_count: Int = -1, var show: MinimizedShow? = null)
data class MostRecomendedShow(var traktId: Int = -1, var user_count: Int = -1, var show: MinimizedShow? = null)
data class MostAnticipatedShow(var traktId: Int = -1, var list_count: Int = -1, var show: MinimizedShow? = null)
data class PopularShow(var traktId: Int = -1, var place: Int = -1, var show: MinimizedShow? = null)
data class Image(var src: String? = null)
data class ShowImages(var traktId: Int = -1, var poster: Image? = null, var seasonPosters: Map<Int, Image> = HashMap(), var banner: Image? = null, var seasonBanners: Map<Int, Image> = HashMap())
data class DetailedShow(
    var title: String = "",
    var year: Int = -1,
    var ids: ReferenceData = ReferenceData(),
    var overview: String = "",
    var firstAired: LocalDateTime? = null,
    var airs: AirTime = AirTime(),
    var runtime: Int = -1,
    var certification: String? = "",
    var network: String? = "",
    var country: String? = "",
    var updatedAt: LocalDateTime? = null,
    var trailer: String? = "",
    var homepage: String? = "",
    var status: String? = "",
    var rating: Int? = -1,
    var votes: Int? = -1,
    var commentCount: Int? = -1,
    var language: String? = "",
    var availableTranslations: List<String>? = ArrayList(),
    var genres: List<String>? = ArrayList(),
    var airedEpisodes: Int? = -1
)

@Parcelize
data class Season(var number: Int = -1, var ids: ReferenceData? = null) : Parcelable
@Parcelize
data class Episode(var season: Int = -1, var number: Int = -1, var title: String = "", var ids: ReferenceData? = null) : Parcelable
data class DetailedEpisode(
    var season: Int = -1,
    var number: Int = -1,
    var title: String = "",
    var ids: ReferenceData? = null,
    var numberAbs: Int? = null,
    var overview: String? = null,
    var firstAired: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var rating: Int? = -1,
    var votes: Int? = -1,
    var commentCount: Int? = -1,
    var availableTranslations: List<String>? = ArrayList(),
    var runtime: Int? = -1
)
data class SearchedShow(var traktId: Int, var score: Int? = -1, var show: MinimizedShow)

data class SeasonWithImages(var season: Season? = null, var images: ShowImages? = null)
data class EpisodeWithImages(var episode: Episode? = null, var images: ShowImages? = null)
data class MinimizedShowWithImages(var show: MinimizedShow? = null, var images: ShowImages? = null)

data class Preferences(var pageSize: String, var timePeriod: String)
