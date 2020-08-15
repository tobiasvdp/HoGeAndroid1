package tech.gim.scroble.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.api.TraktApi
import tech.gim.scroble.model.*
import tech.gim.scroble.repository.ShowRepository
import java.lang.Exception
import javax.inject.Inject

class ShowService(var app: ScrobleApplication) {

    private var mainJob = Job()
    private var coroutineScope = CoroutineScope(mainJob + Dispatchers.Default)

    @Inject()
    lateinit var repository: ShowRepository

    init {
        app.component.inject(this)
    }

    fun getTrendingShows(): LiveData<List<TrendingShow>>{
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshTrendingShows() }
        return repository.trendingShows
    }

    fun getPopularShows(): LiveData<List<PopularShow>>{
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshPopularShows() }
        return repository.popularShows
    }

    fun getMostViewedShows(): LiveData<List<MostViewedShow>>{
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostViewedShows() }
        return repository.mostViewedShows
    }

    fun getMostRecommendedShows(): LiveData<List<MostRecomendedShow>>{
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostRecomendedShows() }
        return repository.mostRecomendedShows
    }

    fun getMostAnticipatedShows(): LiveData<List<MostAnticipatedShow>>{
        app.checkInternetConnectivity()
        coroutineScope.launch { repository.refreshMostAnticipatedShows() }
        return repository.mostAnticipatedShows
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