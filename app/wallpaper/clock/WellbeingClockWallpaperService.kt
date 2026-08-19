package com.droidtank.livewally.wallpaper.clock

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.cos
import kotlin.math.sin

class WellbeingClockWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = WellbeingClockWallpaperEngine()

    inner class WellbeingClockWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val clockPaint = Paint().apply { isAntiAlias = true }
        private val handPaint = Paint().apply { isAntiAlias = true }

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.02f

            val score = currentSnapshot.wellbeingScore
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = (width * 0.4f).coerceAtMost(400f)

            // Draw background
            drawBackground(canvas, width, height, score)

            // Draw clock face
            drawClockFace(canvas, centerX, centerY, radius, score)

            // Draw 24-hour segments
            drawHourSegments(canvas, centerX, centerY, radius, score)

            // Draw step progress ring
            drawStepRing(canvas, centerX, centerY, radius, score)

            // Draw clock hands
            drawClockHands(canvas, centerX, centerY, radius)

            // Draw sleep indicator
            drawSleepIndicator(canvas, centerX, centerY, radius)

            // Draw wellbeing score in center
            drawScoreDisplay(canvas, centerX, centerY, score)
        }

        private fun drawBackground(canvas: Canvas, width: Int, height: Int, score: Float) {
            val colors = when {
                score > 0.7f -> listOf(
                    Color.parseColor("#0D1117"),
                    Color.parseColor("#1D9E75")
                )
                score > 0.4f -> listOf(
                    Color.parseColor("#0a0a1a"),
                    Color.parseColor("#1a2a3a")
                )
                else -> listOf(
                    Color.parseColor("#050505"),
                    Color.parseColor("#1a1a2a")
                )
            }

            val gradient = android.graphics.LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                colors.toIntArray(), null,
                android.graphics.Shader.TileMode.CLAMP
            )
            clockPaint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), clockPaint)
        }

        private fun drawClockFace(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            radius: Float,
            score: Float
        ) {
            // Outer ring
            clockPaint.style = Paint.Style.STROKE
            clockPaint.strokeWidth = 4f
            clockPaint.color = when {
                score > 0.7f -> Color.parseColor("#1D9E75")
                score > 0.4f -> Color.parseColor("#F4A460")
                else -> Color.parseColor("#8B4513")
            }
            clockPaint.alpha = 150
            canvas.drawCircle(cx, cy, radius, clockPaint)

            // Inner circle
            clockPaint.style = Paint.Style.FILL
            clockPaint.color = Color.argb(80, 20, 20, 30)
            canvas.drawCircle(cx, cy, radius * 0.85f, clockPaint)
        }

        private fun drawHourSegments(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            radius: Float,
            score: Float
        ) {
            val screenTimeHours = currentSnapshot.screenTimeMinutes / 60f
            val hours = 24
            val segmentAngle = 360f / hours

            for (i in 0 until hours) {
                val angle = i * segmentAngle - 90
                val isActive = i < screenTimeHours
                val intensity = if (isActive) 1f else 0.2f

                val color = when {
                    score > 0.7f -> {
                        if (isActive) Color.parseColor("#4ECDC4") else Color.parseColor("#1a3a3a")
                    }
                    score > 0.4f -> {
                        if (isActive) Color.parseColor("#F4A460") else Color.parseColor("#2a2a1a")
                    }
                    else -> {
                        if (isActive) Color.parseColor("#FF6347") else Color.parseColor("#1a1010")
                    }
                }

                clockPaint.color = color
                clockPaint.alpha = (intensity * 200).toInt()
                clockPaint.strokeWidth = 3f
                clockPaint.style = Paint.Style.STROKE

                val rect = RectF(
                    cx - radius * 0.9f,
                    cy - radius * 0.9f,
                    cx + radius * 0.9f,
                    cy + radius * 0.9f
                )

                canvas.drawArc(rect, angle, segmentAngle - 2, false, clockPaint)
            }
        }

        private fun drawStepRing(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            radius: Float,
            score: Float
        ) {
            val stepProgress = (currentSnapshot.stepCount / 8000f).coerceIn(0f, 1f)
            val stepColor = when {
                score > 0.7f -> Color.parseColor("#4ECDC4")
                score > 0.4f -> Color.parseColor("#F4A460")
                else -> Color.parseColor("#FF6347")
            }

            clockPaint.color = stepColor
            clockPaint.strokeWidth = 8f
            clockPaint.style = Paint.Style.STROKE
            clockPaint.alpha = 150

            val stepRect = RectF(
                cx - radius * 0.75f,
                cy - radius * 0.75f,
                cx + radius * 0.75f,
                cy + radius * 0.75f
            )

            canvas.drawArc(stepRect, -90f, 360 * stepProgress, false, clockPaint)
        }

        private fun drawClockHands(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            radius: Float
        ) {
            val hours = System.currentTimeMillis().let {
                (it / (1000 * 60 * 60)) % 24
            }.toFloat()
            val minutes = System.currentTimeMillis().let {
                (it / (1000 * 60)) % 60
            }.toFloat()

            // Hour hand
            val hourAngle = (hours + minutes / 60f) * 30f - 90f
            handPaint.color = Color.WHITE
            handPaint.strokeWidth = 6f
            handPaint.style = Paint.Style.STROKE
            handPaint.strokeCap = Paint.Cap.ROUND

            val hourEndX = cx + cos(Math.toRadians(hourAngle.toDouble())).toFloat() * radius * 0.5f
            val hourEndY = cy + sin(Math.toRadians(hourAngle.toDouble())).toFloat() * radius * 0.5f
            canvas.drawLine(cx, cy, hourEndX, hourEndY, handPaint)

            // Minute hand
            val minuteAngle = minutes * 6f - 90f
            handPaint.strokeWidth = 4f

            val minuteEndX = cx + cos(Math.toRadians(minuteAngle.toDouble())).toFloat() * radius * 0.7f
            val minuteEndY = cy + sin(Math.toRadians(minuteAngle.toDouble())).toFloat() * radius * 0.7f
            canvas.drawLine(cx, cy, minuteEndX, minuteEndY, handPaint)

            // Center dot
            handPaint.style = Paint.Style.FILL
            canvas.drawCircle(cx, cy, 8f, handPaint)
        }

        private fun drawSleepIndicator(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            radius: Float
        ) {
            val sleepHours = currentSnapshot.sleepHoursLast
            val sleepRatio = (sleepHours / 9f).coerceIn(0f, 1f)

            val moonX = cx + radius * 0.6f
            val moonY = cy - radius * 0.3f
            val moonRadius = 20f

            // Moon glow
            clockPaint.color = Color.argb(50, 255, 255, 200)
            clockPaint.style = Paint.Style.FILL
            canvas.drawCircle(moonX, moonY, moonRadius * 1.5f, clockPaint)

            // Moon
            clockPaint.color = Color.parseColor("#F5F5DC")
            canvas.drawCircle(moonX, moonY, moonRadius, clockPaint)

            // Moon phase
            clockPaint.color = Color.parseColor("#0D1117")
            val shadowX = moonX + moonRadius * (1 - sleepRatio) * 1.5f
            canvas.drawCircle(shadowX, moonY, moonRadius * 0.9f, clockPaint)

            // Sleep text
            clockPaint.color = Color.WHITE
            clockPaint.textSize = 16f
            clockPaint.textAlign = Paint.Align.CENTER
            canvas.drawText("${sleepHours}h", moonX, moonY + moonRadius + 20, clockPaint)
        }

        private fun drawScoreDisplay(
            canvas: Canvas,
            cx: Float,
            cy: Float,
            score: Float
        ) {
            val scoreText = "${(score * 100).toInt()}"
            val subText = "Wellbeing"

            // Score
            clockPaint.color = when {
                score > 0.7f -> Color.parseColor("#4ECDC4")
                score > 0.4f -> Color.parseColor("#F4A460")
                else -> Color.parseColor("#FF6347")
            }
            clockPaint.textSize = 48f
            clockPaint.textAlign = Paint.Align.CENTER
            clockPaint.style = Paint.Style.FILL
            canvas.drawText(scoreText, cx, cy + 16, clockPaint)

            // Subtext
            clockPaint.color = Color.argb(150, 255, 255, 255)
            clockPaint.textSize = 14f
            canvas.drawText(subText, cx, cy + 40, clockPaint)
        }
    }
}