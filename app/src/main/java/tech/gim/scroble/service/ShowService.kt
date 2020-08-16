package tech.gim.scroble.service

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.*
import tech.gim.scroble.repository.ShowRepository
import javax.inject.Inject

class ShowService(var app: ScrobleApplication) {

    private var mainJob = Job()
    private var coroutineScope = CoroutineScope(mainJob + Dispatchers.Default)

    @Inject()
    lateinit var repository: ShowRepository

    init {
        app.component?.inject(this)
    }

    fun getTrendingShows(): LiveData<List<TrendingShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshTrendingShows() }
        return repository.getTrendingShows()
    }

    fun getPopularShows(): LiveData<List<PopularShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshPopularShows() }
        return repository.getPopularShows()
    }

    fun getMostViewedShows(): LiveData<List<MostViewedShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostViewedShows() }
        return repository.getMostViewedShows()
    }

    fun getMostRecommendedShows(): LiveData<List<MostRecomendedShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostRecomendedShows() }
        return repository.getMostRecommendedShows()
    }

    fun getMostAnticipatedShows(): LiveData<List<MostAnticipatedShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostAnticipatedShows() }
        return repository.getMostAnticipagedShows()
    }

    fun getShowDetails(id: Int): LiveData<DetailedShow?> {
        coroutineScope.launch { repository.refreshDetailedShow(id) }
        return repository.getShowDetailsById(id)
    }

    fun getSeasonsForShow(showId: Int): LiveData<List<Season>> {
        coroutineScope.launch { repository.refreshSeasonsForShow(showId) }
        return repository.getSeasonsForShow(showId)
    }
    fun getEpisodesForSeason(showId: Int, season: Int): LiveData<List<Episode>> {
        coroutineScope.launch { repository.refreshEpisodesForSeason(showId, season) }
        return repository.getEpisodesForSeason(showId, season)
    }
    fun getEpisodeDetail(showId: Int, season: Int, episode: Int): LiveData<DetailedEpisode?> {
        coroutineScope.launch { repository.refreshEpisodeDetails(showId, season, episode) }
        return repository.getEpisodeDetail(showId, season, episode)
    }

    fun getShows(query: String): LiveData<List<SearchedShow>> {
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.searchForShow(query) }
        return repository.getshowsBySearch(query)
    }
}
