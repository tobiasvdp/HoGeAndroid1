package tech.gim.scroble.ui.show.season

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.Episode
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.Season
import tech.gim.scroble.model.ShowImages
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.ShowService
import javax.inject.Inject

class SeasonViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {
    var minimizedShow: MinimizedShow? = null
    var season: Season? = null
    var episodes: LiveData<List<Episode>>? = null
    var images: LiveData<ShowImages?>? = null

    @Inject
    lateinit var showService: ShowService
    @Inject
    lateinit var imageService: ImageService

    init {
        context.component?.inject(this)
    }

    fun setSeason(show: MinimizedShow?, se: Season?) {
        minimizedShow = show
        season = se

        if (minimizedShow != null && season != null) {
            episodes = showService.getEpisodesForSeason(minimizedShow!!.ids!!.trakt!!, season!!.number)
            images = imageService.getImagesForId(minimizedShow!!.ids)
        }
    }
}
