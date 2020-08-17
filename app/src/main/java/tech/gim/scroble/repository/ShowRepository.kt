package tech.gim.scroble.repository

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.api.TraktApi
import tech.gim.scroble.database.ShowDatabaseDAO
import tech.gim.scroble.model.*
import tech.gim.scroble.service.PreferenceService
import timber.log.Timber
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject

/**
 * Repository for shows.
 * The repository offers a way to query for shows,
 * and a function to request more details about a show.
 * This class wraps the database and api into one
 * (allowing for offline/online)
 */
class ShowRepository(val app: ScrobleApplication) {

    @Inject
    lateinit var traktApi: TraktApi
    @Inject
    lateinit var showDatabaseDAO: ShowDatabaseDAO
    @Inject
    lateinit var preferenceService: PreferenceService

    init {
        app.component?.inject(this)
    }

    fun getTrendingShows() = Transformations.map(showDatabaseDAO.getTrendingShows()) {
        it.map { trendingShow -> Mappers.mapToDomain(trendingShow) }
    }
    fun getMostViewedShows() = Transformations.map(showDatabaseDAO.getMostViewedShows()) {
        it.map { show -> Mappers.mapToDomain(show) }
    }
    fun getMostRecommendedShows() = Transformations.map(showDatabaseDAO.getMostRecommendedShows()) {
        it.map { show -> Mappers.mapToDomain(show) }
    }
    fun getMostAnticipagedShows() = Transformations.map(showDatabaseDAO.getMostAnticipatedShows()) {
        it.map { show -> Mappers.mapToDomain(show) }
    }

    fun getPopularShows() = Transformations.map(showDatabaseDAO.getPopularShows()) {
        it.map { show -> Mappers.mapToDomain(show) }
    }

    suspend fun refreshTrendingShows() {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getTrendingShows(limit = preferenceService.preferences.pageSize).await()
                val mappedShows = shows.map {
                    trendingShow ->
                    Mappers.mapToDomain(trendingShow)
                }
                val entityShows = mappedShows.map {
                    trendingShow ->
                    Mappers.mapToEntity(trendingShow)
                }
                showDatabaseDAO.deleteAllTrendingShows()
                showDatabaseDAO.insertAllTrendingShows(entityShows)
            } catch (ex: Exception) {
            }
        }
    }

    suspend fun refreshMostRecomendedShows() {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getMostRecomendedShows(period = preferenceService.preferences.timePeriod, limit = preferenceService.preferences.pageSize).await()
                val mappedShows = shows.map {
                    show ->
                    Mappers.mapToDomain(show)
                }
                val entityShows = mappedShows.map {
                    show ->
                    Mappers.mapToEntity(show)
                }
                showDatabaseDAO.deleteAllMostRecommendedShows()
                showDatabaseDAO.insertAllMostRecommendedShows(entityShows)
            } catch (ex: Exception) {
            }
        }
    }

    suspend fun refreshMostAnticipatedShows() {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getMostAnticipatedShows(limit = preferenceService.preferences.pageSize).await()
                val mappedShows = shows.map {
                    show ->
                    Mappers.mapToDomain(show)
                }
                val entityShows = mappedShows.map {
                    show ->
                    Mappers.mapToEntity(show)
                }
                showDatabaseDAO.deleteAllMostAnticipatedShows()
                showDatabaseDAO.insertAllMostAnticipatedShows(entityShows)
            } catch (ex: Exception) {
            }
        }
    }

    suspend fun refreshMostViewedShows() {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getMostWatchedShows(period = preferenceService.preferences.timePeriod, limit = preferenceService.preferences.pageSize).await()
                val mappedShows = shows.map {
                    show ->
                    Mappers.mapToDomain(show)
                }
                val entityShows = mappedShows.map {
                    show ->
                    Mappers.mapToEntity(show)
                }
                showDatabaseDAO.deleteAllMostViewedShows()
                showDatabaseDAO.insertAllMostViewedShows(entityShows)
            } catch (ex: Exception) {
            }
        }
    }
    suspend fun refreshPopularShows() {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getPopularShows(limit = preferenceService.preferences.pageSize).await()
                var i = 0
                val mappedShows = shows.map {
                    show ->
                    Mappers.mapToDomainAsPopularShow(show, i++)
                }
                val entityShows = mappedShows.map {
                    show ->
                    Mappers.mapToEntity(show)
                }
                showDatabaseDAO.deleteAllPopularShows()
                showDatabaseDAO.insertAllPopularShows(entityShows)
            } catch (ex: Exception) {
            }
        }
    }

    fun getShowDetailsById(id: Int): LiveData<DetailedShow?> = Transformations.map(showDatabaseDAO.getDetailedShow(id)) { Mappers.mapToDomain(it) }
    fun getSeasonsForShow(showId: Int): LiveData<List<Season>> = Transformations.map(showDatabaseDAO.getSeasonsForShow(showId)) { it.map { Mappers.mapToDomain(it) } }
    fun getEpisodesForSeason(showId: Int, season: Int): LiveData<List<Episode>> = Transformations.map(showDatabaseDAO.getEpisodesForSeason(showId, season)) { it.map { Mappers.mapToDomain(it) } }
    fun getEpisodeDetail(showId: Int, season: Int, episode: Int): LiveData<DetailedEpisode?> = Transformations.map(showDatabaseDAO.getDetailedEpisode(showId.toString() + "_" + season.toString() + "_" + episode.toString())) { Mappers.mapToDomain(it) }

    suspend fun refreshDetailedShow(id: Int) {
        withContext(context = Dispatchers.IO) {
            try {
                val showDto = traktApi.getDetailedShow(id.toString()).await()
                val show = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Mappers.mapToDomain(showDto)
                } else {
                    throw RuntimeException("Device to old")
                }
                val entity = Mappers.mapToEntity(show)
                showDatabaseDAO.insertDetailedShow(entity)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    suspend fun refreshSeasonsForShow(showId: Int) {
        withContext(context = Dispatchers.IO) {
            try {
                val seasonsDto = traktApi.getSeasons(showId.toString()).await()
                val seasons = seasonsDto.map { Mappers.mapToDomain(it) }
                val entities = seasons.map { Mappers.mapToEntity(it, showId) }
                showDatabaseDAO.deleteAllSeasonsForShow(showId)
                showDatabaseDAO.insertAllSeasons(entities)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    suspend fun refreshEpisodesForSeason(showId: Int, season: Int) {
        withContext(context = Dispatchers.IO) {
            try {
                val episodesDto = traktApi.getSeasonWthEpisodes(showId.toString(), season.toString()).await()
                val episodes = episodesDto.map { Mappers.mapToDomain(it) }
                val entities = episodes.map { Mappers.mapToEntity(it, showId) }
                showDatabaseDAO.deleteAllEpisodesForSeason(showId, season)
                showDatabaseDAO.insertAllEpisodes(entities)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    suspend fun refreshEpisodeDetails(showId: Int, season: Int, episode: Int) {
        withContext(context = Dispatchers.IO) {
            try {
                val episodeDto = traktApi.getDetailedEpisode(showId.toString(), season.toString(), episode.toString()).await()
                val episode = Mappers.mapToDomain(episodeDto)
                val entity = Mappers.mapToEntity(episode, showId)
                showDatabaseDAO.insertDetailedEpisode(entity)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    fun getshowsBySearch(query: String): LiveData<List<SearchedShow>> = Transformations.map(showDatabaseDAO.getSearchedShowsByQuery("%$query%")) { it.map { Mappers.mapToDomain(it) } }

    suspend fun searchForShow(query: String) {
        withContext(context = Dispatchers.IO) {
            try {
                val shows = traktApi.getShowsBySearch(query = query, limit = preferenceService.preferences.pageSize).await()
                val mappedShows = shows.map {
                    show ->
                    Mappers.mapToDomain(show)
                }
                val entityShows = mappedShows.map {
                    show ->
                    Mappers.mapToEntity(show)
                }
                showDatabaseDAO.insertAllSearchedShows(entityShows)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}
