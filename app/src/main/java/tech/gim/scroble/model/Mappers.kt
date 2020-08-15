package tech.gim.scroble.model

import android.os.Build
import androidx.annotation.RequiresApi
import tech.gim.scroble.model.dto.FanartImage
import tech.gim.scroble.model.dto.FanartImages
import java.lang.Exception
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class Mappers {
    companion object {
        private fun validateNumberNotNull(num: Int?): Int {
            if (num == null)
                throw RuntimeException("Id cannot be null")
            else
                return num
        }

        fun mapToDomain(trendingShow: tech.gim.scroble.model.entity.TrendingShow) = TrendingShow(
            trendingShow.traktId,
            trendingShow.watchers,
            MinimizedShow(
                trendingShow.serie?.title,
                trendingShow.serie?.year,
                trendingShow.serie?.ids
            )
        )

        fun mapToDomain(trendingShow: tech.gim.scroble.model.dto.TrendingShow) = TrendingShow(
            validateNumberNotNull(trendingShow.show?.ids?.trakt),
            trendingShow.watchers,
            MinimizedShow(
                trendingShow.show?.title,
                trendingShow.show?.year,
                trendingShow.show?.ids
            )
        )

        fun mapToEntity(trendingShow: TrendingShow): tech.gim.scroble.model.entity.TrendingShow {
            val show = tech.gim.scroble.model.entity.TrendingShow()
            show.let {
                it.traktId = trendingShow.traktId
                it.watchers = trendingShow.watchers
                it.serie = tech.gim.scroble.model.entity.MinimizedShow(
                    trendingShow.show?.title,
                    trendingShow.show?.year,
                    trendingShow.show?.ids
                )
            }
            return show
        }

        fun mapToDomain(show: tech.gim.scroble.model.dto.ViewedShow) = MostViewedShow(
            validateNumberNotNull(show.show?.ids?.trakt),
            show.watcher_count,
            show.play_count,
            show.collected_count,
            show.collector_count,
            MinimizedShow(
                show.show?.title,
                show.show?.year,
                show.show?.ids
            )
        )
        fun mapToDomain(show: tech.gim.scroble.model.entity.MostViewedShow) = MostViewedShow(
            show.traktId,
            show.watcher_count,
            show.play_count,
            show.collected_count,
            show.collector_count,
            MinimizedShow(
                show.serie?.title,
                show.serie?.year,
                show.serie?.ids
            )
        )
        fun mapToEntity(show: MostViewedShow): tech.gim.scroble.model.entity.MostViewedShow {
            val s = tech.gim.scroble.model.entity.MostViewedShow()
            s.let {
                it.traktId = show.traktId
                it.watcher_count = show.watcher_count
                it.play_count = show.play_count
                it.collected_count = show.collected_count
                it.collector_count = show.collector_count
                it.serie = tech.gim.scroble.model.entity.MinimizedShow(
                    show.show?.title,
                    show.show?.year,
                    show.show?.ids
                )
            }
            return s
        }

        fun mapToDomain(show: tech.gim.scroble.model.dto.MostRecomendedShow) = MostRecomendedShow(
            validateNumberNotNull(show.show?.ids?.trakt),
            show.user_count,
            MinimizedShow(
                show.show?.title,
                show.show?.year,
                show.show?.ids
            )
        )
        fun mapToDomain(show: tech.gim.scroble.model.entity.MostRecomendedShow) = MostRecomendedShow(
            show.traktId,
            show.user_count,
            MinimizedShow(
                show.serie?.title,
                show.serie?.year,
                show.serie?.ids
            )
        )
        fun mapToEntity(show: MostRecomendedShow): tech.gim.scroble.model.entity.MostRecomendedShow {
            val s = tech.gim.scroble.model.entity.MostRecomendedShow()
            s.let {
                it.traktId = show.traktId
                it.user_count = show.user_count
                it.serie = tech.gim.scroble.model.entity.MinimizedShow(
                    show.show?.title,
                    show.show?.year,
                    show.show?.ids
                )
            }
            return s
        }

        fun mapToDomain(show: tech.gim.scroble.model.dto.MostAnticipatedShow) = MostAnticipatedShow(
            validateNumberNotNull(show.show?.ids?.trakt),
            show.list_count,
            MinimizedShow(
                show.show?.title,
                show.show?.year,
                show.show?.ids
            )
        )
        fun mapToDomain(show: tech.gim.scroble.model.entity.MostAnticipatedShow) = MostAnticipatedShow(
            show.traktId,
            show.list_count,
            MinimizedShow(
                show.serie?.title,
                show.serie?.year,
                show.serie?.ids
            )
        )
        fun mapToEntity(show: MostAnticipatedShow): tech.gim.scroble.model.entity.MostAnticipatedShow {
            val s = tech.gim.scroble.model.entity.MostAnticipatedShow()
            s.let {
                it.traktId = show.traktId
                it.list_count = show.list_count
                it.serie = tech.gim.scroble.model.entity.MinimizedShow(
                    show.show?.title,
                    show.show?.year,
                    show.show?.ids
                )
            }
            return s
        }

        fun mapToDomainAsPopularShow(show: tech.gim.scroble.model.dto.Show, place: Int) = PopularShow(
            validateNumberNotNull(show.ids?.trakt),
            place,
            MinimizedShow(
                show.title,
                show.year,
                show.ids
            )
        )
        fun mapToDomain(show: tech.gim.scroble.model.entity.PopularShow) = PopularShow(
            show.traktId,
            show.place,
            MinimizedShow(
                show.serie?.title,
                show.serie?.year,
                show.serie?.ids
            )
        )
        fun mapToEntity(show: PopularShow): tech.gim.scroble.model.entity.PopularShow {
            val s = tech.gim.scroble.model.entity.PopularShow()
            s.let {
                it.traktId = show.traktId
                it.place = show.place
                it.serie = tech.gim.scroble.model.entity.MinimizedShow(
                    show.show?.title,
                    show.show?.year,
                    show.show?.ids
                )
            }
            return s
        }

        fun mapToDomain(img: tech.gim.scroble.model.entity.ShowImages): ShowImages = img.data!!
        fun mapToDomain(imagesByFanart: FanartImages, traktId: Int) = ShowImages(
            traktId,
            selectBestFromImages(imagesByFanart.tvposter),
            selectBestFromImagesBySeason(imagesByFanart.seasonposter),
            selectBestFromImages(imagesByFanart.tvbanner),
            selectBestFromImagesBySeason(imagesByFanart.seasonbanner)
        )

        private fun selectBestFromImagesBySeason(list: List<FanartImage>?): Map<Int, Image> {
            if (list == null || list.isEmpty()) return HashMap()
            var maxVotes = HashMap<String, Int>()
            val highestRatingImage = HashMap<Int, FanartImage>()
            list.iterator().forEach {
                try {
                    if (it.season != null) {
                        val maxVote = maxVotes.getOrPut(it.season!!, { -1 })
                        if (maxVote < it.likes.toInt()) {
                            maxVotes[it.season!!] = it.likes.toInt()
                            highestRatingImage[it.season!!.toInt()] = it
                        }
                    }
                } catch (ex: Exception) {
                }
            }
            return highestRatingImage.mapValues {
                return@mapValues Image(it.value.url)
            }
        }

        private fun selectBestFromImages(list: List<FanartImage>?): Image? {
            if (list == null || list.isEmpty()) return null
            var maxVotes: Int = -1
            var highestRatingImage = list[0]
            list.iterator().forEach {
                try {
                    if (maxVotes < it.likes.toInt()) {
                        maxVotes = it.likes.toInt()
                        highestRatingImage = it
                    }
                } catch (ex: Exception) {
                    if (maxVotes == -1) {
                        maxVotes = 0
                        highestRatingImage = it
                    }
                }
            }
            return Image(highestRatingImage.url)
        }

        fun mapToEntity(images: ShowImages): tech.gim.scroble.model.entity.ShowImages {
            val e = tech.gim.scroble.model.entity.ShowImages()
            e.traktId = images.traktId
            e.data = images
            return e
        }

        fun mapToDomain(it: tech.gim.scroble.model.entity.DetailedShow?): DetailedShow? {
            if (it == null) return null
            return DetailedShow(
                it.title,
                it.year,
                it.ids,
                it.overview,
                it.firstAired,
                mapToDomain(it.airs),
                it.runtime,
                it.certification,
                it.network,
                it.country,
                it.updatedAt,
                it.trailer,
                it.homepage,
                it.status,
                it.rating,
                it.votes,
                it.commentCount,
                it.language,
                it.availableTranslations,
                it.genres,
                it.airedEpisodes
            )
        }

        private fun mapToDomain(airs: tech.gim.scroble.model.entity.AirTime): AirTime = AirTime(airs.day, airs.time, airs.timezone)
        private fun mapToDomain(airs: tech.gim.scroble.model.dto.AirTime) = AirTime(airs.day, airs.time, airs.timezone)
        private fun mapToEntity(airs: AirTime) = tech.gim.scroble.model.entity.AirTime(airs.day, airs.time, airs.timezone)

        @RequiresApi(Build.VERSION_CODES.O)
        fun mapToDomain(it: tech.gim.scroble.model.dto.DetailedShow) = DetailedShow(
            it.title,
            it.year,
            it.ids,
            it.overview,
            LocalDateTime.parse(it.first_aired, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
            mapToDomain(it.airs),
            it.runtime,
            it.certification,
            it.network,
            it.country,
            LocalDateTime.parse(it.updated_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
            it.trailer,
            it.homepage,
            it.status,
            (it.rating?.times(10))?.toInt(),
            it.votes,
            it.comment_count,
            it.language,
            it.available_translations?.toList(),
            it.genres?.toList(),
            it.aired_episodes
        )

        fun mapToEntity(show: DetailedShow): tech.gim.scroble.model.entity.DetailedShow {
            val entity = tech.gim.scroble.model.entity.DetailedShow()
            entity.traktId = validateNumberNotNull(show.ids.trakt)
            entity.title = show.title
            entity.year = show.year
            entity.ids = show.ids
            entity.overview = show.overview
            entity.firstAired = show.firstAired
            entity.airs = mapToEntity(show.airs)
            entity.runtime = show.runtime
            entity.certification = show.certification
            entity.network = show.network
            entity.country = show.country
            entity.updatedAt = show.updatedAt
            entity.trailer = show.trailer
            entity.homepage = show.homepage
            entity.status = show.status.toString()
            entity.rating = show.rating
            entity.votes = show.votes
            entity.commentCount = show.commentCount
            entity.language = show.language
            entity.availableTranslations = show.availableTranslations
            entity.genres = show.genres
            entity.airedEpisodes = show.airedEpisodes
            return entity
        }

        fun mapToDomain(season: tech.gim.scroble.model.dto.Season) = Season(season.number, season.ids)
        fun mapToDomain(season: tech.gim.scroble.model.entity.Season) = Season(season.number, season.ids)
        fun mapToEntity(season: Season, showId: Int): tech.gim.scroble.model.entity.Season {
            val s = tech.gim.scroble.model.entity.Season()
            s.id = showId.toString() + "_" + season.number.toString()
            s.showId = showId
            s.number = season.number
            s.ids = season.ids
            return s
        }

        fun mapToDomain(episode: tech.gim.scroble.model.dto.Episode) = Episode(episode.season, episode.number, episode.title, episode.ids)
        fun mapToDomain(episode: tech.gim.scroble.model.entity.Episode) = Episode(episode.season, episode.number, episode.title, episode.ids)
        fun mapToEntity(episode: Episode, showId: Int): tech.gim.scroble.model.entity.Episode {
            val e = tech.gim.scroble.model.entity.Episode()
            e.id = showId.toString() + "_" + episode.season.toString() + "_" + episode.number.toString()
            e.showId = showId
            e.season = episode.season
            e.number = episode.number
            e.title = episode.title
            e.ids = episode.ids
            return e
        }

        fun mapToDomain(episode: tech.gim.scroble.model.dto.DetailedEpisode) = DetailedEpisode(
            episode.season,
            episode.number,
            episode.title,
            episode.ids,
            episode.number_abs,
            episode.overview,
            LocalDateTime.parse(episode.first_aired, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
            LocalDateTime.parse(episode.updated_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
            (episode.rating?.times(10))?.toInt(),
            episode.votes,
            episode.comment_count,
            episode.available_translations?.toList(),
            episode.runtime
        )
        fun mapToDomain(episode: tech.gim.scroble.model.entity.DetailedEpisode?): DetailedEpisode? {
            if (episode == null)
                return null
            else
                return DetailedEpisode(
                    episode.season,
                    episode.number,
                    episode.title,
                    episode.ids,
                    episode.numberAbs,
                    episode.overview,
                    episode.firstAired,
                    episode.updatedAt,
                    episode.rating,
                    episode.votes,
                    episode.commentCount,
                    episode.availableTranslations,
                    episode.runtime
                )
        }
        fun mapToEntity(episode: DetailedEpisode, showId: Int): tech.gim.scroble.model.entity.DetailedEpisode {
            val e = tech.gim.scroble.model.entity.DetailedEpisode()
            e.id = showId.toString() + "_" + episode.season.toString() + "_" + episode.number.toString()
            e.season = episode.season
            e.number = episode.number
            e.showId = showId
            e.title = episode.title
            e.ids = episode.ids
            e.numberAbs = episode.numberAbs
            e.overview = episode.overview
            e.firstAired = episode.firstAired
            e.updatedAt = episode.updatedAt
            e.rating = episode.rating
            e.votes = episode.votes
            e.commentCount = episode.commentCount
            e.availableTranslations = episode.availableTranslations
            e.runtime = episode.runtime
            return e
        }

        fun mapToDomain(show: tech.gim.scroble.model.dto.SearchedShow) = SearchedShow(
            show.show!!.ids!!.trakt!!,
            (show.score?.times(10))?.toInt(),
            MinimizedShow(
                show.show?.title,
                show.show?.year,
                show.show?.ids
            )
        )

        fun mapToDomain(it: tech.gim.scroble.model.entity.SearchedShow) = SearchedShow(
            it.traktId,
            it.score,
            MinimizedShow(
                it.serie?.title,
                it.serie?.year,
                it.serie?.ids
            )
        )

        fun mapToEntity(show: SearchedShow): tech.gim.scroble.model.entity.SearchedShow {
            val s = tech.gim.scroble.model.entity.SearchedShow()
            s.traktId = show.traktId
            s.score = show.score
            s.serie = tech.gim.scroble.model.entity.MinimizedShow(
                show.show.title,
                show.show.year,
                show.show.ids
            )
            return s
        }
    }
}
