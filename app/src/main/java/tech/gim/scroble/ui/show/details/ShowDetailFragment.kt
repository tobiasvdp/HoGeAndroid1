package tech.gim.scroble.ui.show.details

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import tech.gim.scroble.databinding.SeasonCardBinding
import tech.gim.scroble.databinding.ShowDetailFragmentBinding
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.Season
import tech.gim.scroble.model.SeasonWithImages
import tech.gim.scroble.model.ShowImages
import tech.gim.scroble.utils.ScrobleModelFactory
import timber.log.Timber

class ShowDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ShowDetailFragment()
    }

    private lateinit var viewModel: ShowDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(ShowDetailViewModel::class.java)

        if (arguments != null) {
            val args = ShowDetailFragmentArgs.fromBundle(arguments!!)
            viewModel.setShow(args.minimizedShow)
        }

        val binding: ShowDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.show_detail_fragment, container, false)
        binding.viewModel = viewModel

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = SeasonRecyclerViewAdapter(viewModel.minimizedShow!!, viewModel.images!!, viewModel.seasons!!)
        adapter.dataChanged(viewModel.seasons?.value)
        viewModel.seasons?.observe(viewLifecycleOwner, Observer { adapter.dataChanged(it) })
        viewModel.images?.observe(viewLifecycleOwner, Observer { adapter.dataChanged(it) })
        binding.seasonsList.layoutManager = layoutManager
        binding.seasonsList.adapter = adapter

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.show_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_show_details_share) {
            if (viewModel.minimizedShow?.title != null) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, context?.resources?.getString(R.string.share_show_detail)?.replace("{show}", viewModel.minimizedShow!!.title!!, false))
                    type = "text/plain"
                }
                startActivity(intent)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class SeasonRecyclerViewAdapter(private var show: MinimizedShow, private var images: LiveData<ShowImages?>, private var seasons: LiveData<List<Season>>) : ListAdapter<SeasonWithImages?, SeasonsViewHolder>(SeasonDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        return SeasonsViewHolder.from(parent, show)
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        val seasonWithImages = getItem(position)
        holder.binding.season = seasonWithImages?.season
        holder.binding.seasonPoster = seasonWithImages?.images?.seasonPosters?.get(seasonWithImages.season?.number)?.src
    }

    fun dataChanged(newData: List<Season?>?) {
        if (newData == null) {
            submitList(ArrayList())
        } else {
            Timber.i("Changed dataset seasons")
            val images = images.value
            submitList(newData.map { SeasonWithImages(it, images) })
        }
    }

    fun dataChanged(images: ShowImages?) {
        Timber.i("Changed dataset img")
        submitList(seasons.value?.map { SeasonWithImages(it, images) })
    }
}

class SeasonsViewHolder(val binding: SeasonCardBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup, show: MinimizedShow): SeasonsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SeasonCardBinding.inflate(layoutInflater, parent, false)
            binding.seasonCard.setOnClickListener(
                View.OnClickListener {
                    val dir = ShowDetailFragmentDirections.actionNavShowDetailsToNavShowSeason()
                    dir.minimizedShow = show
                    dir.season = binding.season
                    it.findNavController().navigate(dir)
                }
            )
            return SeasonsViewHolder(binding)
        }
    }
}

class SeasonDiffUtil() : DiffUtil.ItemCallback<SeasonWithImages?>() {
    override fun areItemsTheSame(oldItem: SeasonWithImages, newItem: SeasonWithImages): Boolean {
        return newItem.season?.ids != null && oldItem.season?.ids?.trakt == newItem.season?.ids?.trakt
    }

    override fun areContentsTheSame(oldItem: SeasonWithImages, newItem: SeasonWithImages): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
