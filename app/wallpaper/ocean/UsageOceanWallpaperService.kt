package com.droidtank.livewally.wallpaper.ocean

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.sin

class UsageOceanWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = UsageOceanWallpaperEngine()

    inner class UsageOceanWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val wavePaint = Paint().apply { isAntiAlias = true }
        private val fishPaint = Paint().apply { isAntiAlias = true }

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.05f

            // Ocean gradient based on wellbeing
            val score = currentSnapshot.wellbeingScore
            val screenTimeRatio = (currentSnapshot.screenTimeMinutes / 480f).coerceIn(0f, 1f)
            
            val deepColor = when {
                score > 0.7f -> Color.parseColor("#0a1a2e")
                score > 0.4f -> Color.parseColor("#0f1a3e")
                else -> Color.parseColor("#0a0a1a")
            }
            val shallowColor = when {
                score > 0.7f -> Color.parseColor("#1a3a5c")
                score > 0.4f -> Color.parseColor("#1f2a4e")
                else -> Color.parseColor("#101020")
            }

            val gradient = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                deepColor, shallowColor,
                Shader.TileMode.CLAMP
            )
            wavePaint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), wavePaint)

            // Draw waves
            drawWaves(canvas, width, height, screenTimeRatio)

            // Draw tide indicator
            drawTideIndicator(canvas, width, height, screenTimeRatio)

            // Draw fish
            drawFish(canvas, width, height, score)
        }

        private fun drawWaves(canvas: Canvas, width: Int, height: Int, intensity: Float) {
            val waveCount = 5
            val baseHeight = height * 0.6f
            
            for (i in 0 until waveCount) {
                val waveY = baseHeight + i * (height * 0.08f)
                val waveAmplitude = (20 + i * 10) * (0.5f + intensity * 0.5f)
                val waveSpeed = 0.02f + i * 0.01f

                wavePaint.color = Color.argb(
                    (30 + i * 20).toInt(),
                    100, 180, 255
                )
                wavePaint.strokeWidth = 3f
                wavePaint.style = Paint.Style.STROKE

                val path = android.graphics.Path()
                path.moveTo(0f, waveY)

                for (x in 0..width step 10) {
                    val y = waveY + sin(x * 0.02f + time * waveSpeed + i) * waveAmplitude
                    path.lineTo(x.toFloat(), y)
                }

                canvas.drawPath(path, wavePaint)
            }
        }

        private fun drawTideIndicator(canvas: Canvas, width: Int, height: Int, tideLevel: Float) {
            val indicatorX = width * 0.1f
            val indicatorY = height * 0.1f
            val indicatorWidth = width * 0.2f
            val indicatorHeight = 20f

            // Background
            wavePaint.color = Color.argb(100, 0, 0, 0)
            wavePaint.style = Paint.Style.FILL
            canvas.drawRoundRect(
                indicatorX, indicatorY,
                indicatorX + indicatorWidth, indicatorY + indicatorHeight,
                10f, 10f, wavePaint
            )

            // Tide level
            val tideColor = when {
                tideLevel < 0.3f -> Color.parseColor("#4ECDC4")
                tideLevel < 0.6f -> Color.parseColor("#F4A460")
                else -> Color.parseColor("#FF6347")
            }
            wavePaint.color = tideColor
            canvas.drawRoundRect(
                indicatorX, indicatorY,
                indicatorX + indicatorWidth * tideLevel,
                indicatorY + indicatorHeight,
                10f, 10f, wavePaint
            )
        }

        private fun drawFish(canvas: Canvas, width: Int, height: Int, score: Float) {
            val fishCount = (3 + score * 5).toInt()
            val notificationRatio = (currentSnapshot.notificationCount / 50f).coerceIn(0f, 1f)

            for (i in 0 until fishCount) {
                val fishX = (width * 0.1f + (width * 0.8f * i / fishCount) + sin(time * 2 + i) * 50)
                    .coerceIn(50f, width - 50f)
                val fishY = height * (0.3f + i * 0.1f)

                // Fish scatter during high notification/unlock spikes
                val scatter = if (notificationRatio > 0.7f) {
                    sin(time * 5 + i * 3) * 30 * notificationRatio
                } else 0f

                val fishColor = when {
                    score > 0.7f -> Color.parseColor("#4ECDC4")
                    score > 0.4f -> Color.parseColor("#F4A460")
                    else -> Color.parseColor("#8B4513")
                }

                drawFishShape(canvas, fishX + scatter, fishY, 15f, fishColor, time * 2 + i)
            }
        }

        private fun drawFishShape(
            canvas: Canvas,
            x: Float,
            y: Float,
            size: Float,
            color: Int,
            angle: Float
        ) {
            fishPaint.color = color
            fishPaint.style = Paint.Style.FILL

            // Body
            canvas.drawCircle(x, y, size, fishPaint)

            // Tail
            val tailPath = android.graphics.Path()
            tailPath.moveTo(x - size, y)
            tailPath.lineTo(x - size * 2, y - size * 0.8f)
            tailPath.lineTo(x - size * 2, y + size * 0.8f)
            tailPath.close()
            canvas.drawPath(tailPath, fishPaint)

            // Eye
            fishPaint.color = Color.WHITE
            canvas.drawCircle(x + size * 0.3f, y - size * 0.2f, size * 0.3f, fishPaint)
            fishPaint.color = Color.BLACK
            canvas.drawCircle(x + size * 0.4f, y - size * 0.2f, size * 0.15f, fishPaint)
        }
    }
}