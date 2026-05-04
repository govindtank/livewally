package com.example.livewally.data.source

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounter: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private var stepCount = 0
    private var lastUpdate = 0L
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
                val currentTime = System.currentTimeMillis()
                if (lastUpdate == 0L) {
                    lastUpdate = currentTime
                    stepCount = event.values[0].toInt()
                } else if (currentTime - lastUpdate > 1000) { // Update every second
                    lastUpdate = currentTime
                    stepCount = event.values[0].toInt()
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        stepCounter?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun getStepCount(): Int {
        return stepCount
    }

    fun getBatteryInfo(): Pair<Int, Boolean> {
        // In a real implementation, this would use BatteryManager
        // For now, return simulated values
        val batteryPercent = (System.currentTimeMillis() / 1000).toInt() % 101
        val isCharging = batteryPercent > 50 // Simulate charging when >50%
        return batteryPercent to isCharging
    }

    fun cleanup() {
        stepCounter?.let { sensorManager.unregisterListener(sensorEventListener, it) }
    }
}