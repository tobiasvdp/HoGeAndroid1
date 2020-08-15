package tech.gim.scroble.ui.show.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import tech.gim.scroble.R
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.databinding.SearchShowsFragmentBinding
import tech.gim.scroble.ui.show.ShowsRecyclerViewAdapter
import tech.gim.scroble.utils.HelperFunctions
import tech.gim.scroble.utils.ScrobleModelFactory

class SearchShowsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchShowsFragment()
    }

    private lateinit var viewModel: SearchShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(
            SearchShowsViewModel::class.java
        )

        if (arguments != null) {
            viewModel.setSearch(arguments!!.getString("search", "Two and a half men"))
        }
        val binding: SearchShowsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.search_shows_fragment, container, false)
        binding.viewModel = viewModel

        val adapter = ShowsRecyclerViewAdapter(
            onClickHandler = { view, minimizedShow ->
                val dir = SearchShowsFragmentDirections.actionNavSearchShowsToNavShowDetails()
                dir.minimizedShow = minimizedShow
                view.findNavController().navigate(dir)
            }
        )
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
