package tech.gim.scroble.ui.show.collections

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.MinimizedShowWithImages
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.ShowService
import tech.gim.scroble.utils.HelperFunctions
import javax.inject.Inject

class SearchShowsViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {
    lateinit var shows: LiveData<List<MinimizedShow?>>
    lateinit var showsWithImages: LiveData<List<MinimizedShowWithImages>>
    var query = ""

    @Inject
    lateinit var showService: ShowService
    @Inject
    lateinit var imageService: ImageService

    init {
        context.component?.inject(this)
    }

    fun setSearch(s: String) {
        query = s
        shows = Transformations.map(showService.getShows(s)) { it.map { it.show } }
        showsWithImages = Transformations.switchMap(shows) {
            Transformations.map(imageService.getImagesForIds(it.map { it?.ids })) { img -> HelperFunctions.combineShowsAndImages(it, img) }
        }
    }
}
