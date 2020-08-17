package tech.gim.scroble.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import tech.gim.scroble.BuildConfig
import tech.gim.scroble.model.dto.FanartImages

/**
 * Retrofit api for fanart service
 * This api provides list of images related to a show.
 * Searchable by tvdbId
 */
interface FanartApi {
    @GET("tv/{tvdbId}?api_key=" + BuildConfig.FANART_API_KEY)
    fun getImagesForTvdbId(@Path("tvdbId") tvdbId: String): Deferred<FanartImages>
}
