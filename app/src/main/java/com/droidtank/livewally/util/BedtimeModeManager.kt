package com.droidtank.livewally.util

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BedtimeModeManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager
) {
    
    fun enableBedtimeMode() {
        try {
            // Enable Zen Mode (DND) - simplified implementation
            @Suppress("DEPRECATION")
            notificationManager.setInterruptionFilter(2) // INTERRUPTION_FILTER_PRIORITY
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disableBedtimeMode() {
        try {
            @Suppress("DEPRECATION")
            notificationManager.setInterruptionFilter(0) // INTERRUPTION_FILTER_ALL
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isBedtimeModeActive(): Boolean {
        return try {
            @Suppress("DEPRECATION")
            notificationManager.getCurrentInterruptionFilter() == 2
        } catch (e: Exception) {
            false
        }
    }
}
