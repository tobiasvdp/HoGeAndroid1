package tech.gim.scroble.ui.show.episode

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tech.gim.scroble.R
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.databinding.EpisodeFragmentBinding
import tech.gim.scroble.databinding.SeasonFragmentBinding
import tech.gim.scroble.ui.show.details.ShowDetailFragmentArgs
import tech.gim.scroble.ui.show.details.ShowDetailViewModel
import tech.gim.scroble.ui.show.season.EpisodeRecyclerViewAdapter
import tech.gim.scroble.utils.ScrobleModelFactory

class EpisodeFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodeFragment()
    }

    private lateinit var viewModel: EpisodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(EpisodeViewModel::class.java)

        if(arguments != null) {
            val args = EpisodeFragmentArgs.fromBundle(arguments!!)
            viewModel.setEpisode(args.minimizedShow, args.season, args.episode)
        }

        val binding: EpisodeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.episode_fragment, container, false)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        return binding.root

        return inflater.inflate(R.layout.episode_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}