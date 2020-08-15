package tech.gim.scroble.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.api.FanartApi
import tech.gim.scroble.database.ImagesDatabaseDAO
import tech.gim.scroble.model.Mappers
import tech.gim.scroble.model.ReferenceData
import tech.gim.scroble.model.ShowImages
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ImageRepository(app: ScrobleApplication) {

    @Inject
    lateinit var api: FanartApi

    @Inject
    lateinit var dao: ImagesDatabaseDAO

    init {
        app.component.inject(this)
    }

    fun getImagesForShowId(ids: ReferenceData?): LiveData<ShowImages?> {
        val showImage = dao.getImagesByTraktId(ids?.trakt!!)
        return Transformations.map(showImage) {
            if (it == null) return@map null
            return@map Mappers.mapToDomain(it)
        }
    }

    fun getImagesForShowIdSync(ids: ReferenceData?): ShowImages? {
        val showImage = dao.getImagesByTraktIdSync(ids?.trakt!!)
        if (showImage == null) return null
        return Mappers.mapToDomain(showImage)
    }

    fun getImagesForShowIds(ids: List<ReferenceData?>): LiveData<List<ShowImages?>> {
        val showImages = dao.getImagesByTraktIds(ids.map { it!!.trakt!! })
        return Transformations.map(showImages) { it.map { Mappers.mapToDomain(it) } }
    }

    fun getImagesForShowIdsSync(ids: List<ReferenceData?>): List<ShowImages> {
        val showImages = dao.getImagesByTraktIdsSync(ids.map { it!!.trakt!! })
        return showImages.map { Mappers.mapToDomain(it) }
    }

    suspend fun fetchImages(ids: ReferenceData?) {
        if (ids?.trakt == null) return
        Timber.i("Fetching images for %s", ids.trakt)

        withContext(Dispatchers.IO) {
            val job = api.getImagesForTvdbId(ids.tvdb.toString())
            try {
                val imagesByFanart = job.await()
                val images = Mappers.mapToDomain(imagesByFanart, ids.trakt!!)
                val entity = Mappers.mapToEntity(images)
                dao.insert(entity)
            } catch (ex: Exception) {
                Timber.e("Failed to proccess images %s", ex.localizedMessage)
            }
        }
    }
}
