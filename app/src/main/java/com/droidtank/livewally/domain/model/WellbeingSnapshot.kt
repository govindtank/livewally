package com.droidtank.livewally.domain.model

data class WellbeingSnapshot(
    val screenTimeMinutes: Int,
    val unlockCount: Int,
    val notificationCount: Int,
    val stepCount: Int,
    val sleepHoursLast: Float,
    val heartRateAvg: Int?,
    val batteryPercent: Int,
    val isCharging: Boolean,
    val topAppCategory: AppCategory,
    val wellbeingScore: Float,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        fun default(): WellbeingSnapshot {
            return WellbeingSnapshot(
                screenTimeMinutes = 0,
                unlockCount = 0,
                notificationCount = 0,
                stepCount = 0,
                sleepHoursLast = 0f,
                heartRateAvg = null,
                batteryPercent = 100,
                isCharging = false,
                topAppCategory = AppCategory.OTHER,
                wellbeingScore = 0.5f
            )
        }
    }
}

enum class AppCategory { PRODUCTIVE, SOCIAL, ENTERTAINMENT, COMMUNICATION, OTHER }