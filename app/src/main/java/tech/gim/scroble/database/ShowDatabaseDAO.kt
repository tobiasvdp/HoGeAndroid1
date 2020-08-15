package tech.gim.scroble.database

import androidx.lifecycle.LiveData
import androidx.room.*
import tech.gim.scroble.model.entity.*

@Dao
interface ShowDatabaseDAO {
    @Query("SELECT * FROM SEASONS WHERE SHOW_ID = :showId")
    fun getSeasonsForShow(showId: Int): LiveData<List<Season>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSeasons(seasons: List<Season>)
    @Query("DELETE FROM SEASONS WHERE SHOW_ID = :showId")
    fun deleteAllSeasonsForShow(showId: Int)

    @Query("SELECT * FROM EPISODES WHERE SEASON = :season AND SHOW_ID = :showId")
    fun getEpisodesForSeason(showId: Int, season: Int): LiveData<List<Episode>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEpisodes(episodes: List<Episode>)
    @Query("DELETE FROM EPISODES WHERE SHOW_ID = :showId AND SEASON = :season")
    fun deleteAllEpisodesForSeason(showId: Int, season: Int)

    @Query("SELECT * FROM EPISODES_DETAILS WHERE CONCAT_ID = :episodeId")
    fun getDetailedEpisode(episodeId: String): LiveData<DetailedEpisode?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailedEpisode(detailedEpisode: DetailedEpisode)

    @Query("SELECT * FROM SHOWS")
    fun getAllShows(): LiveData<List<DetailedShow>>

    @Query("SELECT * FROM SHOWS WHERE TRAKT_ID = :id")
    fun getDetailedShow(id: Int): LiveData<DetailedShow>

    @Query("DELETE FROM SHOWS")
    fun deleteAllShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailedShow(detailedShows: DetailedShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDetailedShows(detailedShows: List<DetailedShow>)

    @Query("SELECT * FROM TRENDING_SHOWS ORDER BY WATCHERS DESC")
    fun getTrendingShows(): LiveData<List<TrendingShow>>

    @Query("DELETE FROM TRENDING_SHOWS")
    fun deleteAllTrendingShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTrendingShows(trendingShow: List<TrendingShow>)

    @Query("SELECT * FROM MOST_ANTICIPATED_SHOWS ORDER BY LIST_COUNT DESC")
    fun getMostAnticipatedShows(): LiveData<List<MostAnticipatedShow>>

    @Query("DELETE FROM MOST_ANTICIPATED_SHOWS")
    fun deleteAllMostAnticipatedShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMostAnticipatedShows(show: List<MostAnticipatedShow>)

    @Query("SELECT * FROM MOST_RECOMENDED_SHOWS ORDER BY USER_COUNT DESC")
    fun getMostRecommendedShows(): LiveData<List<MostRecomendedShow>>

    @Query("DELETE FROM MOST_RECOMENDED_SHOWS")
    fun deleteAllMostRecommendedShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMostRecommendedShows(show: List<MostRecomendedShow>)

    @Query("SELECT * FROM MOST_VIEWED_SHOWS ORDER BY WATCHER_COUNT DESC")
    fun getMostViewedShows(): LiveData<List<MostViewedShow>>

    @Query("DELETE FROM MOST_VIEWED_SHOWS")
    fun deleteAllMostViewedShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMostViewedShows(trendingShow: List<MostViewedShow>)

    @Query("SELECT * FROM POPULAR_SHOWS ORDER BY PLACE ASC")
    fun getPopularShows(): LiveData<List<PopularShow>>

    @Query("DELETE FROM POPULAR_SHOWS")
    fun deleteAllPopularShows()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPopularShows(trendingShow: List<PopularShow>)

    @Query("SELECT * FROM SEARCHED_SHOWS WHERE TITLE LIKE :query ORDER BY SCORE DESC")
    fun getSearchedShowsByQuery(query: String?): LiveData<List<SearchedShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSearchedShows(searchedShows: List<SearchedShow>)
}
