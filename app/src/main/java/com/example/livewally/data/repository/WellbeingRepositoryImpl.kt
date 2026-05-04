package com.example.livewally.data.repository

import android.content.Context
import com.example.livewally.data.model.WellbeingSnapshot
import com.example.livewally.data.source.HealthConnectDataSource
import com.example.livewally.data.source.NotificationDataSource
import com.example.livewally.data.source.SensorDataSource
import com.example.livewally.data.source.UsageStatsDataSource
import com.example.livewally.data.model.AppCategory
import com.example.livewally.domain.repository.WellbeingRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WellbeingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val usageStatsDataSource: UsageStatsDataSource,
    private val healthConnectDataSource: HealthConnectDataSource,
    private val notificationDataSource: NotificationDataSource,
    private val sensorDataSource: SensorDataSource
) : WellbeingRepository {

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private val _snapshot = MutableStateFlow(WellbeingSnapshot.default())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    
    override val snapshot: StateFlow<WellbeingSnapshot> = _snapshot.asStateFlow()
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    override val error: StateFlow<String?> = _error.asStateFlow()

    init {
        startSnapshotCollection()
    }

    override fun observeSnapshot(): kotlinx.coroutines.flow.Flow<WellbeingSnapshot> = snapshot

    override suspend fun getSnapshot(): WellbeingSnapshot = snapshot.value

    override suspend fun refresh() = refreshSnapshot()

    private fun startSnapshotCollection() {
        repositoryScope.launch {
            while (true) {
                try {
                    _isLoading.value = true
                    val newSnapshot = collectSnapshot()
                    _snapshot.value = newSnapshot
                    _error.value = null
                    kotlinx.coroutines.delay(30_000)
                } catch (e: Exception) {
                    _error.value = "Failed to update data: ${e.message}"
                    kotlinx.coroutines.delay(5_000)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    suspend fun refreshSnapshot() = withContext(Dispatchers.IO) {
        try {
            _isLoading.value = true
            val newSnapshot = collectSnapshot()
            _snapshot.value = newSnapshot
            _error.value = null
        } catch (e: Exception) {
            _error.value = "Failed to refresh: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }

    private suspend fun collectSnapshot(): WellbeingSnapshot = coroutineScope {
        val screenTime = usageStatsDataSource.getTodayScreenTimeMinutes()
        val unlockCount = usageStatsDataSource.getUnlockCount()
        val notificationCount = notificationDataSource.getNotificationCount()
        
        val stepCountDeferred = async { healthConnectDataSource.getTodaySteps() }
        val sleepHoursDeferred = async { healthConnectDataSource.getLastSleepHours() }
        val heartRateDeferred = async { healthConnectDataSource.getAvgHeartRate() }
        
        val stepCount = stepCountDeferred.await()
        val sleepHours = sleepHoursDeferred.await()
        val heartRate = heartRateDeferred.await()
        
        val (batteryPercent, isCharging) = sensorDataSource.getBatteryInfo()
        val stepCountFromSensor = sensorDataSource.getStepCount()
        
        val finalStepCount = maxOf(stepCount.toInt(), stepCountFromSensor)
        
        val topAppCategory = determineTopAppCategory(usageStatsDataSource.getPerAppUsageToday())
        val wellbeingScore = computeWellbeingScore(
            screenTime,
            finalStepCount,
            sleepHours.toFloat(),
            notificationCount
        )
        
        WellbeingSnapshot(
            screenTimeMinutes = screenTime,
            unlockCount = unlockCount,
            notificationCount = notificationCount,
            stepCount = finalStepCount,
            sleepHoursLast = sleepHours.toFloat(),
            heartRateAvg = heartRate,
            batteryPercent = batteryPercent,
            isCharging = isCharging,
            topAppCategory = topAppCategory,
            wellbeingScore = wellbeingScore
        )
    }

    private fun determineTopAppCategory(usageMap: Map<String, Long>): AppCategory {
        return when (usageMap.maxByOrNull { it.value }?.key) {
            "com.android.chrome", "org.telegram.messenger" -> AppCategory.SOCIAL
            "com.google.android.youtube", "com.netflix.mediaclient" -> AppCategory.ENTERTAINMENT
            "com.google.android.gm", "com.microsoft.office.excel" -> AppCategory.PRODUCTIVE
            else -> AppCategory.OTHER
        }
    }

    private fun computeWellbeingScore(
        screenTimeMinutes: Int,
        stepCount: Int,
        sleepHours: Float,
        notificationCount: Int
    ): Float {
        val screenScore = (1f - (screenTimeMinutes / 480f).coerceIn(0f, 1f))
        val stepScore = (stepCount / 8000f).coerceIn(0f, 1f)
        val sleepScore = when {
            sleepHours in 7.0..9.0 -> 1f
            sleepHours in 6.0..7.0 -> 0.6f
            sleepHours < 6.0 -> 0.2f
            else -> 0.8f
        }
        val notificationScore = (1f - (notificationCount / 100f).coerceIn(0f, 1f))
        
        return (screenScore * 0.3f + stepScore * 0.25f + sleepScore * 0.25f + notificationScore * 0.2f)
            .coerceIn(0f, 1f)
    }
}