package tech.gim.scroble.ui.show.episode

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.*
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.ShowService
import javax.inject.Inject

class EpisodeViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {
    var minimizedShow: MinimizedShow? = null
    var season: Season? = null
    var episode: Episode? = null
    var detailedEpisode: LiveData<DetailedEpisode?>? = null
    var images: LiveData<ShowImages?>? = null
    var posterSrc: LiveData<String?>? = null

    @Inject
    lateinit var showService: ShowService
    @Inject
    lateinit var imageService: ImageService

    init {
        context.component.inject(this)
    }

    fun setEpisode(show: MinimizedShow?, se: Season?, ep: Episode?) {
        minimizedShow = show
        season = se
        episode = ep

        if(minimizedShow != null && season != null && episode != null){
            detailedEpisode = showService.getEpisodeDetail(minimizedShow!!.ids!!.trakt!!, episode!!.season, episode!!.number)
            images = imageService.getImagesForId(minimizedShow!!.ids)
            posterSrc = Transformations.map(images!!) {
                var src = it?.seasonBanners?.get(episode!!.season)?.src
                if(src == null){
                    src = it?.banner?.src
                }
                return@map src
            }
        }
    }
}