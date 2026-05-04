package com.example.livewally.wallpaper.breath

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.example.livewally.data.model.WellbeingSnapshot
import com.example.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.sin
import kotlin.math.PI

class BreathingWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = BreathingWallpaperEngine()

    inner class BreathingWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val breathPaint = Paint().apply { isAntiAlias = true }
        private var animationPhase = 0f

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.05f
            animationPhase = (sin(time.toDouble()) * 0.5f + 0.5f).toFloat()

            // Calculate colors based on wellbeing score and screen time
            val score = currentSnapshot.wellbeingScore
            val screenTimeRatio = (currentSnapshot.screenTimeMinutes / 480f).coerceIn(0f, 1f)
            
            val colors = when {
                score > 0.7f -> {
                    // Healthy - teal/green gradient
                    listOf(
                        Color.parseColor("#1B6B4A"),
                        Color.parseColor("#0D9E75"),
                        Color.parseColor("#4ECDC4")
                    )
                }
                score > 0.4f -> {
                    // Moderate - amber gradient
                    listOf(
                        Color.parseColor("#B8860B"),
                        Color.parseColor("#DAA520"),
                        Color.parseColor("#F4A460")
                    )
                }
                else -> {
                    // Low - rose/red gradient
                    listOf(
                        Color.parseColor("#8B0000"),
                        Color.parseColor("#DC143C"),
                        Color.parseColor("#FF6347")
                    )
                }
            }

            // Adjust colors based on screen time
            val adjustedColors = colors.map { color ->
                val r = Color.red(color)
                val g = Color.green(color)
                val b = Color.blue(color)
                val darken = 1f - screenTimeRatio * 0.3f
                Color.rgb(
                    (r * darken).toInt(),
                    (g * darken).toInt(),
                    (b * darken).toInt()
                )
            }

            // Draw gradient background
            val gradient = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                adjustedColors.toIntArray(),
                null,
                Shader.TileMode.CLAMP
            )
            breathPaint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), breathPaint)

            // Draw breathing orbs
            drawBreathingOrbs(canvas, width, height, animationPhase, score)

            // Draw wellbeing score indicator
            drawScoreIndicator(canvas, width, height, score)
        }

        private fun drawBreathingOrbs(
            canvas: Canvas,
            width: Int,
            height: Int,
            phase: Float,
            score: Float
        ) {
            val centerX = width / 2f
            val centerY = height / 2f
            val baseRadius = (width * 0.15f).coerceAtMost(200f)
            val breathRadius = baseRadius * (0.8f + phase * 0.4f)

            // Main breathing orb
            breathPaint.color = Color.argb(
                (100 + phase * 100).toInt(),
                255,
                255,
                255
            )
            breathPaint.style = Paint.Style.FILL
            canvas.drawCircle(centerX, centerY, breathRadius, breathPaint)

            // Inner orb
            breathPaint.color = Color.argb(
                (150 + phase * 100).toInt(),
                255,
                255,
                255
            )
            canvas.drawCircle(centerX, centerY, breathRadius * 0.6f, breathPaint)

            // Satellite orbs
            val satelliteCount = 5
            for (i in 0 until satelliteCount) {
                val angle = (i * 2 * PI / satelliteCount + time * 0.02).toFloat()
                val distance = baseRadius * (1.5f + phase * 0.5f)
                val sx = centerX + cos(angle) * distance
                val sy = centerY + sin(angle) * distance
                val satelliteRadius = baseRadius * 0.3f * (0.7f + phase * 0.3f)

                breathPaint.color = Color.argb(
                    (80 + phase * 80).toInt(),
                    255,
                    255,
                    255
                )
                canvas.drawCircle(sx, sy, satelliteRadius, breathPaint)
            }
        }

        private fun drawScoreIndicator(
            canvas: Canvas,
            width: Int,
            height: Int,
            score: Float
        ) {
            val indicatorX = width * 0.9f
            val indicatorY = height * 0.1f
            val radius = 30f

            // Background circle
            breathPaint.color = Color.argb(100, 0, 0, 0)
            breathPaint.style = Paint.Style.FILL
            canvas.drawCircle(indicatorX, indicatorY, radius + 10, breathPaint)

            // Score arc
            breathPaint.color = when {
                score > 0.7f -> Color.parseColor("#4ECDC4")
                score > 0.4f -> Color.parseColor("#F4A460")
                else -> Color.parseColor("#FF6347")
            }
            breathPaint.style = Paint.Style.STROKE
            breathPaint.strokeWidth = 4f
            canvas.drawCircle(indicatorX, indicatorY, radius, breathPaint)

            // Score text
            breathPaint.color = Color.WHITE
            breathPaint.textSize = 24f
            breathPaint.textAlign = Paint.Align.CENTER
            breathPaint.style = Paint.Style.FILL
            canvas.drawText(
                "${(score * 100).toInt()}",
                indicatorX,
                indicatorY + 8,
                breathPaint
            )
        }

        private fun cos(angle: Float) = kotlin.math.cos(angle.toDouble()).toFloat()
    }
}