package tech.gim.scroble.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tech.gim.scroble.model.dto.*
import tech.gim.scroble.model.dto.Show

interface TraktApi {

    @GET("/search/show")
    fun getShowsBySearch(@Query("query") query: String, @Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<SearchedShow>>

    @GET("/shows/trending")
    fun getTrendingShows(@Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<TrendingShow>>

    @GET("/shows/popular")
    fun getPopularShows(@Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<Show>>

    @GET("/shows/recommended/{period}")
    fun getMostRecomendedShows(@Path("period") period: String, @Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<MostRecomendedShow>>

    @GET("/shows/watched/{period}")
    fun getMostWatchedShows(@Path("period") period: String, @Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<ViewedShow>>

    @GET("/shows/anticipated")
    fun getMostAnticipatedShows(@Query("page") page: String? = "1", @Query("limit") limit: String? = "27"): Deferred<Array<MostAnticipatedShow>>

    @GET("/shows/{traktId}?extended=full")
    fun getDetailedShow(@Path("traktId") traktId: String): Deferred<DetailedShow>

    @GET("/shows/{traktId}/seasons")
    fun getSeasons(@Path("traktId") traktId: String): Deferred<List<Season>>

    @GET("/shows/{traktId}/seasons/{season}")
    fun getSeasonWthEpisodes(@Path("traktId") traktId: String, @Path("season") season: String): Deferred<List<Episode>>

    @GET("/shows/{traktId}/seasons/{season}/episodes/{episode}?extended=full")
    fun getDetailedEpisode(@Path("traktId") traktId: String, @Path("season") season: String, @Path("episode") episode: String): Deferred<DetailedEpisode>
}
