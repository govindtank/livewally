package com.example.livewally.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class AppSettings(
    val frameRate: Int = 30,
    val batterySaverEnabled: Boolean = false,
    val bedtimeModeEnabled: Boolean = false,
    val bedtimeTime: String = "22:00",
    val wakeTime: String = "07:00",
    val stepGoal: Int = 10000,
    val screenTimeLimit: Int = 300,
    val sleepGoal: Float = 8f
)

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.settingsDataStore

    val appSettings: Flow<AppSettings> = dataStore.data
        .map { preferences ->
            AppSettings(
                frameRate = preferences[PreferencesKeys.FRAME_RATE] ?: 30,
                batterySaverEnabled = preferences[PreferencesKeys.BATTERY_SAVER] ?: false,
                bedtimeModeEnabled = preferences[PreferencesKeys.BEDTIME_MODE] ?: false,
                bedtimeTime = preferences[PreferencesKeys.BEDTIME_TIME] ?: "22:00",
                wakeTime = preferences[PreferencesKeys.WAKE_TIME] ?: "07:00",
                stepGoal = preferences[PreferencesKeys.STEP_GOAL] ?: 10000,
                screenTimeLimit = preferences[PreferencesKeys.SCREEN_TIME_LIMIT] ?: 300,
                sleepGoal = preferences[PreferencesKeys.SLEEP_GOAL] ?: 8f
            )
        }

    suspend fun updateFrameRate(frameRate: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FRAME_RATE] = frameRate
        }
    }

    suspend fun updateBatterySaver(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BATTERY_SAVER] = enabled
        }
    }

    suspend fun updateBedtimeMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEDTIME_MODE] = enabled
        }
    }

    suspend fun updateBedtimeTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEDTIME_TIME] = time
        }
    }

    suspend fun updateWakeTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.WAKE_TIME] = time
        }
    }

    suspend fun updateStepGoal(goal: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEP_GOAL] = goal
        }
    }

    suspend fun updateScreenTimeLimit(limit: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCREEN_TIME_LIMIT] = limit
        }
    }

    suspend fun updateSleepGoal(goal: Float) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SLEEP_GOAL] = goal
        }
    }

    private object PreferencesKeys {
        val FRAME_RATE = intPreferencesKey("frame_rate")
        val BATTERY_SAVER = booleanPreferencesKey("battery_saver")
        val BEDTIME_MODE = booleanPreferencesKey("bedtime_mode")
        val BEDTIME_TIME = stringPreferencesKey("bedtime_time")
        val WAKE_TIME = stringPreferencesKey("wake_time")
        val STEP_GOAL = intPreferencesKey("step_goal")
        val SCREEN_TIME_LIMIT = intPreferencesKey("screen_time_limit")
        val SLEEP_GOAL = floatPreferencesKey("sleep_goal")
    }
}