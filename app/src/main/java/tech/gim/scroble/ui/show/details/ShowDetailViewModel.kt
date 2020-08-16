package tech.gim.scroble.ui.show.details

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.DetailedShow
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.Season
import tech.gim.scroble.model.ShowImages
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.ShowService
import javax.inject.Inject

class ShowDetailViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {
    var minimizedShow: MinimizedShow? = null
    var detailedShow: LiveData<DetailedShow?>? = null
    var seasons: LiveData<List<Season>>? = null
    var images: LiveData<ShowImages?>? = null

    @Inject
    lateinit var showService: ShowService
    @Inject
    lateinit var imageService: ImageService

    init {
        context.component?.inject(this)
    }

    fun setShow(show: MinimizedShow?) {
        minimizedShow = show
        if (minimizedShow != null) {
            minimizedShow!!.ids?.trakt?.let {
                detailedShow = showService.getShowDetails(it)
                seasons = showService.getSeasonsForShow(it)
                images = imageService.getImagesForId(minimizedShow!!.ids)
            }
        }
    }
}
