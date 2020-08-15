package tech.gim.scroble.ui.show.collections

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import tech.gim.scroble.R
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.databinding.AnticipatedShowsFragmentBinding
import tech.gim.scroble.databinding.MostViewedShowsFragmentBinding
import tech.gim.scroble.ui.show.ShowsRecyclerViewAdapter
import tech.gim.scroble.utils.HelperFunctions
import tech.gim.scroble.utils.ScrobleModelFactory

class MostViewedShowsFragment : Fragment() {

    companion object {
        fun newInstance() = MostViewedShowsFragment()
    }

    private lateinit var viewModel: MostViewedShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(
            MostViewedShowsViewModel::class.java)
        val binding: MostViewedShowsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.most_viewed_shows_fragment, container, false)
        binding.viewModel = viewModel

        val adapter = ShowsRecyclerViewAdapter(onClickHandler = { view, minimizedShow ->
            val dir = MostViewedShowsFragmentDirections.actionNavShowsMostViewedToNavShowDetails()
            dir.minimizedShow = minimizedShow
            view.findNavController().navigate(dir)
        })
        adapter.dataChanged(viewModel.showsWithImages.value)
        viewModel.showsWithImages.observe(viewLifecycleOwner, Observer { adapter.dataChanged(it) })
        binding.showsList.adapter = adapter
        binding.showsList.layoutManager = GridLayoutManager(context, HelperFunctions.calculateNoOfColumns(context, HelperFunctions.getCardWidth(context)))

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}