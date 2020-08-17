package tech.gim.scroble.service

import android.content.SharedPreferences
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.Preferences

/**
 * Service for Preferences
 * Preferences can be freely changed during the application,
 * But a @{commitPreferenceChanges} is required to persist the changes
 * This service does not abstragate away the data layer to repository, because this is an android feature
 */
class PreferenceService(var app: ScrobleApplication) {

    private val storedPreferences: SharedPreferences = app.getSharedPreferences("ScroblePreferences", 0)
    val preferences: Preferences

    init {
        app.component?.inject(this)
        preferences = Preferences(
            storedPreferences.getString("pageSize", "27")!!,
            storedPreferences.getString("timePeriod", "weekly")!!
        )
    }

    fun commitPreferenceChanges() {
        val editor = storedPreferences.edit()
        editor.putString("pageSize", preferences.pageSize)
        editor.putString("timePeriod", preferences.timePeriod)
        editor.apply()
    }
}
