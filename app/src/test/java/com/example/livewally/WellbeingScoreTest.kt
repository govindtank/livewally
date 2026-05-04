package com.example.livewally

import com.example.livewally.data.model.WellbeingSnapshot
import com.example.livewally.data.model.AppCategory
import org.junit.Assert.*
import org.junit.Test

class WellbeingScoreTest {

    @Test
    fun `computeWellbeingScore returns 1.0 for perfect values`() {
        val snapshot = WellbeingSnapshot(
            screenTimeMinutes = 0,
            stepCount = 8000,
            sleepHoursLast = 8f,
            notificationCount = 0,
            unlockCount = 0,
            heartRateAvg = null,
            batteryPercent = 100,
            isCharging = false,
            topAppCategory = AppCategory.PRODUCTIVE,
            wellbeingScore = 0f
        )

        val score = computeWellbeingScore(snapshot)
        assertEquals(1.0f, score, 0.01f)
    }

    @Test
    fun `computeWellbeingScore returns low score for heavy usage`() {
        val snapshot = WellbeingSnapshot(
            screenTimeMinutes = 480, // 8 hours
            stepCount = 1000,
            sleepHoursLast = 5f,
            notificationCount = 200,
            unlockCount = 0,
            heartRateAvg = null,
            batteryPercent = 20,
            isCharging = false,
            topAppCategory = AppCategory.ENTERTAINMENT,
            wellbeingScore = 0f
        )

        val score = computeWellbeingScore(snapshot)
        assertTrue(score < 0.3f)
    }

    @Test
    fun `computeWellbeingScore returns moderate score for average usage`() {
        val snapshot = WellbeingSnapshot(
            screenTimeMinutes = 180, // 3 hours
            stepCount = 5000,
            sleepHoursLast = 6.5f,
            notificationCount = 50,
            unlockCount = 0,
            heartRateAvg = null,
            batteryPercent = 50,
            isCharging = false,
            topAppCategory = AppCategory.OTHER,
            wellbeingScore = 0f
        )

        val score = computeWellbeingScore(snapshot)
        assertTrue(score in 0.4f..0.7f)
    }

    @Test
    fun `computeWellbeingScore handles edge case of 0 screen time`() {
        val snapshot = WellbeingSnapshot(
            screenTimeMinutes = 0,
            stepCount = 10000,
            sleepHoursLast = 9f,
            notificationCount = 0,
            unlockCount = 0,
            heartRateAvg = null,
            batteryPercent = 100,
            isCharging = true,
            topAppCategory = AppCategory.PRODUCTIVE,
            wellbeingScore = 0f
        )

        val score = computeWellbeingScore(snapshot)
        assertEquals(1.0f, score, 0.01f)
    }

    @Test
    fun `computeWellbeingScore handles edge case of max screen time`() {
        val snapshot = WellbeingSnapshot(
            screenTimeMinutes = 600,
            stepCount = 0,
            sleepHoursLast = 3f,
            notificationCount = 300,
            unlockCount = 0,
            heartRateAvg = null,
            batteryPercent = 10,
            isCharging = false,
            topAppCategory = AppCategory.ENTERTAINMENT,
            wellbeingScore = 0f
        )

        val score = computeWellbeingScore(snapshot)
        assertTrue(score < 0.2f)
    }

    @Test
    fun `sleep score is 1.0 for 7-9 hours`() {
        val goodSleepScores = listOf(7f, 7.5f, 8f, 8.5f, 9f)
        
        goodSleepScores.forEach { hours ->
            val score = sleepScore(hours)
            assertEquals(1.0f, score, 0.01f)
        }
    }

    @Test
    fun `sleep score is 0.6 for 6-7 hours`() {
        val fairSleepScores = listOf(6f, 6.3f, 6.5f, 6.8f)
        
        fairSleepScores.forEach { hours ->
            val score = sleepScore(hours)
            assertEquals(0.6f, score, 0.01f)
        }
    }

    @Test
    fun `sleep score is 0.2 for less than 6 hours`() {
        val poorSleepScores = listOf(0f, 2f, 4f, 5.9f)
        
        poorSleepScores.forEach { hours ->
            val score = sleepScore(hours)
            assertEquals(0.2f, score, 0.01f)
        }
    }

    @Test
    fun `sleep score is 0.4 for more than 9 hours`() {
        val oversleepScores = listOf(9.1f, 10f, 12f)
        
        oversleepScores.forEach { hours ->
            val score = sleepScore(hours)
            assertEquals(0.4f, score, 0.01f)
        }
    }

    private fun computeWellbeingScore(snapshot: WellbeingSnapshot): Float {
        val screenScore = (1f - (snapshot.screenTimeMinutes / 480f).coerceIn(0f, 1f))
        val stepScore = (snapshot.stepCount / 8000f).coerceIn(0f, 1f)
        val sleepScore = sleepScore(snapshot.sleepHoursLast)
        val notifScore = (1f - (snapshot.notificationCount / 200f).coerceIn(0f, 1f))
        
        return (screenScore * 0.35f + stepScore * 0.25f + sleepScore * 0.25f + notifScore * 0.15f)
            .coerceIn(0f, 1f)
    }

    private fun sleepScore(hours: Float): Float {
        return when {
            hours in 7.0..9.0 -> 1f
            hours in 6.0..7.0 -> 0.6f
            hours < 6.0 -> 0.2f
            else -> 0.4f
        }
    }
}
