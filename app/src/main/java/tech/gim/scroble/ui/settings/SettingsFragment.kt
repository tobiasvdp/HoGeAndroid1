package tech.gim.scroble.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import tech.gim.scroble.R
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.databinding.SettingsFragmentBinding
import tech.gim.scroble.utils.ScrobleModelFactory

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, ScrobleModelFactory(context?.applicationContext as ScrobleApplication)).get(
            SettingsViewModel::class.java
        )
        val binding: SettingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.viewModel = viewModel
        binding.settingsPeriodField.setSelection(getIndexOfSelected(viewModel.preferences.timePeriod), false)
        binding.settingsPeriodField.onItemSelectedListener = viewModel.onPeriodSelection
        binding.setLifecycleOwner(this)
        return binding.root
    }

    private fun getIndexOfSelected(timePeriod: String): Int {
        val arr = context?.resources?.getStringArray(R.array.settings_period_array)
        if (arr != null) {
            return arr.indexOf(timePeriod)
        }
        return 0
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onPause() {
        viewModel.confirmChanges()
        super.onPause()
    }
}
