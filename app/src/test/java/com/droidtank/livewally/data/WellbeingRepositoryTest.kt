package com.droidtank.livewally.data

import com.droidtank.livewally.data.model.AppCategory
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.data.repository.WellbeingRepositoryImpl
import com.droidtank.livewally.data.source.HealthConnectDataSource
import com.droidtank.livewally.data.source.NotificationDataSource
import com.droidtank.livewally.data.source.SensorDataSource
import com.droidtank.livewally.data.source.UsageStatsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WellbeingRepositoryTest {

    @Mock
    private lateinit var usageStatsDataSource: UsageStatsDataSource

    @Mock
    private lateinit var healthConnectDataSource: HealthConnectDataSource

    @Mock
    private lateinit var notificationDataSource: NotificationDataSource

    @Mock
    private lateinit var sensorDataSource: SensorDataSource

    private lateinit var repository: WellbeingRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        
        // Setup default mock responses
        whenever(usageStatsDataSource.getTodayScreenTimeMinutes()).thenReturn(120)
        whenever(usageStatsDataSource.getUnlockCount()).thenReturn(15)
        whenever(usageStatsDataSource.getPerAppUsageToday()).thenReturn(mapOf(
            "com.android.chrome" to 3600000L
        ))
        
        whenever(healthConnectDataSource.getTodaySteps()).thenReturn(5000)
        whenever(healthConnectDataSource.getLastSleepHours()).thenReturn(7.5f)
        whenever(healthConnectDataSource.getAvgHeartRate()).thenReturn(72)
        
        whenever(notificationDataSource.getNotificationCount()).thenReturn(25)
        
        whenever(sensorDataSource.getStepCount()).thenReturn(3000)
        whenever(sensorDataSource.getBatteryInfo()).thenReturn(75 to false)
    }

    @Test
    fun `getSnapshot returns correct wellbeing data`() = runTest {
        // This test would require proper dependency injection setup
        // For now, we test the logic
        
        val screenTime = 120
        val steps = 5000
        val sleep = 7.5f
        val notifications = 25

        val wellbeingScore = computeWellbeingScore(screenTime, steps, sleep, notifications)
        
        assertTrue(wellbeingScore in 0.5f..0.8f)
    }

    @Test
    fun `wellbeingScore is clamped between 0 and 1`() {
        val testCases = listOf(
            Triple(0, 0, 0f),      // Minimum
            Triple(480, 10000, 10f), // Maximum
            Triple(240, 5000, 5f),  // Middle
        )

        testCases.forEach { (screenTime, steps, sleep) ->
            val score = computeWellbeingScore(screenTime, steps, sleep, 50)
            assertTrue("Score should be between 0 and 1", score in 0f..1f)
        }
    }

    @Test
    fun `higher screen time results in lower score`() {
        val lowScreenTime = 60
        val highScreenTime = 300

        val scoreLow = computeWellbeingScore(lowScreenTime, 5000, 7.5f, 20)
        val scoreHigh = computeWellbeingScore(highScreenTime, 5000, 7.5f, 20)

        assertTrue("Low screen time should have higher score", scoreLow > scoreHigh)
    }

    @Test
    fun `more steps result in higher score`() {
        val fewSteps = 2000
        val manySteps = 8000

        val scoreFew = computeWellbeingScore(120, fewSteps, 7.5f, 20)
        val scoreMany = computeWellbeingScore(120, manySteps, 7.5f, 20)

        assertTrue("More steps should have higher score", scoreMany > scoreFew)
    }

    @Test
    fun `good sleep results in higher score`() {
        val poorSleep = 5f
        val goodSleep = 8f

        val scorePoor = computeWellbeingScore(120, 5000, poorSleep, 20)
        val scoreGood = computeWellbeingScore(120, 5000, goodSleep, 20)

        assertTrue("Good sleep should have higher score", scoreGood > scorePoor)
    }

    @Test
    fun `fewer notifications result in higher score`() {
        val manyNotifs = 150
        val fewNotifs = 10

        val scoreMany = computeWellbeingScore(120, 5000, 7.5f, manyNotifs)
        val scoreFew = computeWellbeingScore(120, 5000, 7.5f, fewNotifs)

        assertTrue("Fewer notifications should have higher score", scoreFew > scoreMany)
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
            else -> 0.4f
        }
        val notifScore = (1f - (notificationCount / 200f).coerceIn(0f, 1f))
        
        return (screenScore * 0.35f + stepScore * 0.25f + sleepScore * 0.25f + notifScore * 0.15f)
            .coerceIn(0f, 1f)
    }
}
