package tech.gim.scroble.service

import android.content.SharedPreferences
import tech.gim.scroble.ScrobleApplication
import tech.gim.scroble.model.Preferences

class PreferenceService(var app: ScrobleApplication) {

    private val storedPreferences: SharedPreferences = app.getSharedPreferences("ScroblePreferences", 0)
    val preferences: Preferences

    init {
        app.component.inject(this)
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
