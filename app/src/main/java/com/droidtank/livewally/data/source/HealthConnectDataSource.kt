package com.droidtank.livewally.data.source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthConnectDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Health Connect SDK is not available - stub implementation
    // To enable Health Connect, add the health-connect dependency and implement proper client
    
    fun isAvailable(): Boolean = false
    
    suspend fun getTodaySteps(): Long = 0L
    
    suspend fun getLastSleepHours(): Long = 0L
    
    suspend fun getAvgHeartRate(): Int = 0
}
