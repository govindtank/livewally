package com.droidtank.livewally.data.source

import android.app.usage.UsageStats
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Calendar

@Singleton
class UsageStatsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val usageManager = context.getSystemService(UsageStatsManager::class.java)

    fun getTodayScreenTimeMinutes(): Int {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val stats = usageManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )
        return ((stats?.sumOf { it.totalTimeInForeground } ?: 0L) / 60_000).toInt()
    }

    fun getPerAppUsageToday(): Map<String, Long> {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val stats = usageManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            cal.timeInMillis,
            System.currentTimeMillis()
        )
        val usageMap = mutableMapOf<String, Long>()
        stats?.forEach { stat ->
            usageMap[stat.packageName] = (usageMap[stat.packageName] ?: 0) + stat.totalTimeInForeground
        }
        return usageMap
    }

    fun getUnlockCount(): Int {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val events = usageManager.queryEvents(
            cal.timeInMillis,
            System.currentTimeMillis()
        )
        var count = 0
        val event = UsageEvents.Event()
        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.KEYGUARD_HIDDEN) {
                count++
            }
        }
        return count
    }
}
