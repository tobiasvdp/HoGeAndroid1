package tech.gim.scroble.ui.settings

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.AndroidViewModel
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.Preferences
import tech.gim.scroble.service.PreferenceService
import javax.inject.Inject

class SettingsViewModel(private val context: ScrobleApplication) : AndroidViewModel(context) {

    @Inject
    lateinit var preferenceService: PreferenceService
    val preferences: Preferences
    val onPeriodSelection = PeriodSelectionListener(this)

    init {
        context.component.inject(this)
        preferences = preferenceService.preferences
    }

    fun confirmChanges() {
        preferenceService.commitPreferenceChanges()
    }
}

class PeriodSelectionListener(val settingsViewModel: SettingsViewModel) : OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        settingsViewModel.preferences.timePeriod = parent.getItemAtPosition(pos) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}
