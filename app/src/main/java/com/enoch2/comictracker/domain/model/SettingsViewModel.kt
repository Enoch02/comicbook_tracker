package com.enoch2.comictracker.domain.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//TODO: add setting options that prevent the app from loading data online
class SettingsViewModel : ViewModel() {
    private val darkModeKey = booleanPreferencesKey("dark_mode")
    private val exitDialogKey = booleanPreferencesKey("exit_dialog")

    fun switchDarkModeValue(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                val currentDarkModeValue = settings[darkModeKey] ?: false
                settings[darkModeKey] = !currentDarkModeValue
            }
        }
    }

    fun getDarkModeValue(context: Context): Flow<Boolean> {
        val darkModeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[darkModeKey] ?: false
        }
        return darkModeFlow
    }

    fun switchExitDialogValue(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                val currentExitDialogValue = settings[exitDialogKey] ?: false
                settings[exitDialogKey] = !currentExitDialogValue
            }
        }
    }

    fun getExitDialogValue(context: Context): Flow<Boolean> {
        val exitDialogFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[exitDialogKey] ?: false
        }
        return exitDialogFlow
    }
}
