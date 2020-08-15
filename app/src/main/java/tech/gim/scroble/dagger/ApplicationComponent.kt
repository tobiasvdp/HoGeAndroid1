package tech.gim.scroble.dagger

import dagger.Component
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.repository.ImageRepository
import tech.gim.scroble.repository.ShowRepository
import tech.gim.scroble.service.ImageService
import tech.gim.scroble.service.PreferenceService
import tech.gim.scroble.service.ShowService
import tech.gim.scroble.ui.settings.SettingsViewModel
import tech.gim.scroble.ui.show.collections.*
import tech.gim.scroble.ui.show.details.ShowDetailViewModel
import tech.gim.scroble.ui.show.episode.EpisodeViewModel
import tech.gim.scroble.ui.show.season.SeasonViewModel
import javax.inject.Singleton

@Component(modules = arrayOf(ApplicationModule::class))
@Singleton
interface ApplicationComponent {
    fun inject(app: ScrobleApplication);
    fun inject(showService: ShowService);
    fun inject(showRepository: ShowRepository)
    fun inject(imageRepository: ImageRepository)
    fun inject(imageService: ImageService)
    fun inject(showDetailViewModel: ShowDetailViewModel)
    fun inject(seasonViewModel: SeasonViewModel)
    fun inject(episodeViewModel: EpisodeViewModel)
    fun inject(trendingShowsViewModel: TrendingShowsViewModel)
    fun inject(popularShowsViewModel: PopularShowsViewModel)
    fun inject(mostViewedShowsViewModel: MostViewedShowsViewModel)
    fun inject(mostRecomendedShowsViewModel: MostRecomendedShowsViewModel)
    fun inject(anticipatedShowsViewModel: AnticipatedShowsViewModel)
    fun inject(settingsViewModel: SettingsViewModel)
    fun inject(preferenceService: PreferenceService)
    fun inject(searchShowsViewModel: SearchShowsViewModel)
}