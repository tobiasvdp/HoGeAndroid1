package tech.gim.scroble.model.dto

import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.ReferenceData

data class Show(var title: String = "", var year: Int = -1, var ids: ReferenceData = ReferenceData())
data class SearchedShow(var type: String? = "show", var score: Double? = -1.0, var show: MinimizedShow? = null)
data class TrendingShow(var show: Show? = null, var watchers: Int = -1)
data class ViewedShow(var watcher_count: Int = -1, var play_count: Int = -1, var collected_count: Int = -1, var collector_count: Int = -1, var show: Show? = null)
data class MostRecomendedShow(var user_count: Int = -1, var show: Show? = null)
data class MostAnticipatedShow(var list_count: Int = -1, var show: Show? = null)
data class AirTime(var day: String? = "", var time: String? = "", var timezone: String? = "")
data class DetailedShow(
    var title: String = "",
    var year: Int = -1,
    var ids: ReferenceData = ReferenceData(),
    var overview: String = "",
    var first_aired: String? = "",
    var airs: AirTime = AirTime(),
    var runtime: Int = -1,
    var certification: String? = "",
    var network: String? = "",
    var country: String? = "",
    var updated_at: String? = "",
    var trailer: String? = "",
    var homepage: String? = "",
    var status: String? = "",
    var rating: Double? = -1.0,
    var votes: Int? = -1,
    var comment_count: Int? = -1,
    var language: String? = "",
    var available_translations: Array<String>? = arrayOf(),
    var genres: Array<String>? = arrayOf(),
    var aired_episodes: Int? = -1
)
data class Season(var number: Int = -1, var ids: ReferenceData? = null)
data class Episode(var season: Int = -1, var number: Int = -1, var title: String = "", var ids: ReferenceData? = null)
data class DetailedEpisode(
    var season: Int = -1,
    var number: Int = -1,
    var title: String = "",
    var ids: ReferenceData? = null,
    var number_abs: Int? = null,
    var overview: String? = null,
    var first_aired: String? = null,
    var updated_at: String? = null,
    var rating: Double? = -1.0,
    var votes: Int? = -1,
    var comment_count: Int? = -1,
    var available_translations: Array<String>? = arrayOf(),
    var runtime: Int? = -1
)
