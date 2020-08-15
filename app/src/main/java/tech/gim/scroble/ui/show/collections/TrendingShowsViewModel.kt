package tech.gim.scroble.ui.show.collections

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.MinimizedShowWithImages
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.ShowService
import tech.gim.scroble.utils.HelperFunctions
import javax.inject.Inject

class TrendingShowsViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {
    var shows: LiveData<List<MinimizedShow?>>
    var showsWithImages: LiveData<List<MinimizedShowWithImages>>

    @Inject
    lateinit var showService: ShowService
    @Inject
    lateinit var imageService: ImageService

    init {
        context.component.inject(this)
        shows = Transformations.map(showService.getTrendingShows()) { it.map { it.show } }
        showsWithImages = Transformations.switchMap(shows)  {
            Transformations.map(imageService.getImagesForIds(it.map { it?.ids })){img -> HelperFunctions.combineShowsAndImages(it, img) }
        }
    }
}