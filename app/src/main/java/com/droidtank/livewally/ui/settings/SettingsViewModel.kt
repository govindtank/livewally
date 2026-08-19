package com.droidtank.livewally.ui.settings

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidtank.livewally.data.store.AppSettings
import com.droidtank.livewally.data.store.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        checkPermissions()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsDataStore.appSettings.collect { settings ->
                _uiState.update {
                    it.copy(
                        frameRate = settings.frameRate,
                        batterySaverEnabled = settings.batterySaverEnabled,
                        bedtimeModeEnabled = settings.bedtimeModeEnabled,
                        bedtimeTime = settings.bedtimeTime,
                        wakeTime = settings.wakeTime,
                        stepGoal = settings.stepGoal,
                        screenTimeLimit = settings.screenTimeLimit,
                        sleepGoal = settings.sleepGoal
                    )
                }
            }
        }
    }

    private fun checkPermissions() {
        val usageStatsGranted = checkUsageStatsPermission()
        _uiState.update { it.copy(usageStatsPermissionGranted = usageStatsGranted) }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    fun setFrameRate(frameRate: Int) {
        viewModelScope.launch {
            settingsDataStore.updateFrameRate(frameRate)
        }
    }

    fun setBatterySaver(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.updateBatterySaver(enabled)
        }
    }

    fun setBedtimeMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.updateBedtimeMode(enabled)
        }
    }

    fun setBedtimeTime(time: String) {
        viewModelScope.launch {
            settingsDataStore.updateBedtimeTime(time)
        }
    }

    fun setWakeTime(time: String) {
        viewModelScope.launch {
            settingsDataStore.updateWakeTime(time)
        }
    }

    fun setStepGoal(goal: Int) {
        viewModelScope.launch {
            settingsDataStore.updateStepGoal(goal)
        }
    }

    fun setScreenTimeLimit(limit: Int) {
        viewModelScope.launch {
            settingsDataStore.updateScreenTimeLimit(limit)
        }
    }

    fun setSleepGoal(goal: Float) {
        viewModelScope.launch {
            settingsDataStore.updateSleepGoal(goal)
        }
    }

    fun openUsageStatsSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openNotificationListenerSettings() {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}

data class SettingsUiState(
    val frameRate: Int = 30,
    val batterySaverEnabled: Boolean = false,
    val bedtimeModeEnabled: Boolean = false,
    val bedtimeTime: String = "22:00",
    val wakeTime: String = "07:00",
    val stepGoal: Int = 10000,
    val screenTimeLimit: Int = 300,
    val sleepGoal: Float = 8f,
    val usageStatsPermissionGranted: Boolean = false
)