package tech.gim.scroble.ui.show.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.gim.scroble.R
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.databinding.EpisodeCardBinding
import tech.gim.scroble.databinding.SeasonCardBinding
import tech.gim.scroble.databinding.SeasonFragmentBinding
import tech.gim.scroble.databinding.ShowDetailFragmentBinding
import tech.gim.scroble.model.*
import tech.gim.scroble.ui.show.details.SeasonRecyclerViewAdapter
import tech.gim.scroble.ui.show.details.ShowDetailFragment
import tech.gim.scroble.ui.show.details.ShowDetailFragmentDirections
import tech.gim.scroble.ui.show.episode.EpisodeFragmentArgs
import tech.gim.scroble.ui.show.episode.EpisodeViewModel
import tech.gim.scroble.utils.ScrobleModelFactory
import timber.log.Timber

class SeasonFragment : Fragment() {

    companion object {
        fun newInstance() = SeasonFragment()
    }

    private lateinit var viewModel: SeasonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(
            SeasonViewModel::class.java)

        if(arguments != null) {
            val args = SeasonFragmentArgs.fromBundle(arguments!!)
            viewModel.setSeason(args.minimizedShow, args.season)
        }

        val binding: SeasonFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.season_fragment, container, false)

        val adapter = EpisodeRecyclerViewAdapter(viewModel.minimizedShow!!, viewModel.season!!, viewModel.images!!, viewModel.episodes!!)
        adapter.dataChanged(viewModel.episodes?.value)
        viewModel.episodes?.observe(viewLifecycleOwner, Observer { adapter.dataChanged(it) })
        viewModel.images?.observe(viewLifecycleOwner, Observer { adapter.dataChanged(it) })
        binding.episodesList.adapter = adapter

        binding.setLifecycleOwner(this)
        return binding.root
    }
}

class EpisodeRecyclerViewAdapter(private var show: MinimizedShow, private var season: Season, private var images: LiveData<ShowImages?>, private var episodes: LiveData<List<Episode>>): ListAdapter<EpisodeWithImages?, EpisodeViewHolder>(EpisodeDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent, show, season)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episodeWithImages = getItem(position)
        holder.binding.episode = episodeWithImages?.episode
        holder.binding.poster = episodeWithImages?.images?.seasonPosters?.get(season?.number)?.src
        if(holder.binding.poster == null) holder.binding.poster = episodeWithImages?.images?.poster?.src
    }

    fun dataChanged(newData: List<Episode?>?) {
        if(newData == null){
            submitList(ArrayList())
        }else{
            Timber.i("Changed dataset seasons")
            val images = images.value
            submitList(newData.map { EpisodeWithImages(it, images) })
        }
    }

    fun dataChanged(images: ShowImages?) {
        Timber.i("Changed dataset img")
        submitList(episodes.value?.map { EpisodeWithImages(it, images) })
    }


}

class EpisodeViewHolder(val binding: EpisodeCardBinding): RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun from(parent: ViewGroup, show: MinimizedShow, season: Season): EpisodeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EpisodeCardBinding.inflate(layoutInflater, parent, false)
            binding.episodeCard.setOnClickListener(View.OnClickListener {
                val dir = SeasonFragmentDirections.actionNavShowSeasonToNavShowEpisode()
                dir.minimizedShow = show
                dir.season = season
                dir.episode = binding.episode
                it.findNavController().navigate(dir)
            })
            return EpisodeViewHolder(binding)
        }
    }
}

class EpisodeDiffUtil(): DiffUtil.ItemCallback<EpisodeWithImages?>(){
    override fun areItemsTheSame(oldItem: EpisodeWithImages, newItem: EpisodeWithImages): Boolean {
        return newItem.episode?.ids != null && oldItem.episode?.ids?.trakt == newItem.episode?.ids?.trakt
    }

    override fun areContentsTheSame(oldItem: EpisodeWithImages, newItem: EpisodeWithImages): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}