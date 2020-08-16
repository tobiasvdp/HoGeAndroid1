package tech.gim.scroble.service

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.ReferenceData
import tech.gim.scroble.model.ShowImages
import tech.gim.scroble.repository.ImageRepository
import javax.inject.Inject

class ImageService(app: ScrobleApplication) {

    @Inject
    lateinit var imageRepository: ImageRepository

    private var mainJob = Job()
    private var coroutineScope = CoroutineScope(mainJob + Dispatchers.Default)

    init {
        app.component?.inject(this)
    }

    fun getImagesForId(ref: ReferenceData?): LiveData<ShowImages?> {
        val img = imageRepository.getImagesForShowId(ref)
        coroutineScope.launch {
            val i = imageRepository.getImagesForShowIdSync(ref)
            if (i == null) {
                imageRepository.fetchImages(ref)
            }
        }
        return img
    }

    fun getImagesForIds(ref: List<ReferenceData?>): LiveData<List<ShowImages?>> {
        val img = imageRepository.getImagesForShowIds(ref)
        coroutineScope.launch {
            val presentIds = imageRepository.getImagesForShowIdsSync(ref).map { it.traktId }
            val notFoundRefs = ref.filter { referenceData -> !(presentIds.contains(referenceData?.trakt)) }
            notFoundRefs.iterator().forEach { imageRepository.fetchImages(it) }
        }
        return img
    }

    fun getImagesForIdsSync(ref: List<ReferenceData?>): List<ShowImages> {
        return imageRepository.getImagesForShowIdsSync(ref)
    }
}
