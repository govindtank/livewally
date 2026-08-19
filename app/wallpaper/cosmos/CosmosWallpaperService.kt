package com.droidtank.livewally.wallpaper.cosmos

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.cos
import kotlin.math.sin

class CosmosWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = CosmosWallpaperEngine()

    inner class CosmosWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val starPaint = Paint().apply { isAntiAlias = true }
        private val stars = mutableListOf<Star>()

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            initializeStars(width, height)
        }

        private fun initializeStars(width: Int, height: Int) {
            stars.clear()
            for (i in 0..100) {
                stars.add(Star(
                    x = (Math.random() * width).toFloat(),
                    y = (Math.random() * height * 0.8).toFloat(),
                    size = (Math.random() * 3 + 1).toFloat(),
                    brightness = Math.random().toFloat()
                ))
            }
        }

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.02f

            // Dark cosmos background
            canvas.drawColor(Color.parseColor("#050810"))

            // Draw stars
            drawStars(canvas, width, height)

            // Draw constellation lines
            drawConstellations(canvas, width, height)

            // Draw moon (sleep indicator)
            drawMoon(canvas, width, height)

            // Draw shooting stars
            drawShootingStars(canvas, width, height)
        }

        private fun drawStars(canvas: Canvas, width: Int, height: Int) {
            val score = currentSnapshot.wellbeingScore
            stars.forEach { star ->
                val twinkle = (sin(time * 3 + star.x) * 0.3f + 0.7f)
                val brightness = star.brightness * twinkle
                val alpha = (brightness * 255 * (0.5f + score * 0.5f)).toInt()

                starPaint.color = Color.argb(alpha, 255, 255, 255)
                canvas.drawCircle(star.x, star.y, star.size, starPaint)

                // Glow effect for bright stars
                if (brightness > 0.7f) {
                    starPaint.color = Color.argb((alpha / 4).toInt(), 100, 150, 255)
                    canvas.drawCircle(star.x, star.y, star.size * 2, starPaint)
                }
            }
        }

        private fun drawConstellations(canvas: Canvas, width: Int, height: Int) {
            val score = currentSnapshot.wellbeingScore
            val historyDays = 7
            val dayWidth = width / historyDays.toFloat()

            starPaint.strokeWidth = 2f
            starPaint.style = Paint.Style.STROKE

            for (day in 0 until historyDays - 1) {
                val dayScore = score * (0.8f + day * 0.05f).coerceAtMost(1f)
                val color = when {
                    dayScore > 0.7f -> Color.parseColor("#4ECDC4")
                    dayScore > 0.4f -> Color.parseColor("#F4A460")
                    else -> Color.parseColor("#8B4513")
                }

                starPaint.color = Color.argb((dayScore * 200).toInt(), Color.red(color), Color.green(color), Color.blue(color))

                val startX = day * dayWidth + dayWidth / 2
                val startY = height * (0.2f + (1 - dayScore) * 0.5f)
                val endX = (day + 1) * dayWidth + dayWidth / 2
                val endY = height * (0.2f + (1 - score) * 0.5f)

                canvas.drawLine(startX, startY, endX, endY, starPaint)
            }
        }

        private fun drawMoon(canvas: Canvas, width: Int, height: Int) {
            val sleepHours = currentSnapshot.sleepHoursLast
            val moonPhase = (sleepHours / 9f).coerceIn(0f, 1f)
            val moonX = width * 0.8f
            val moonY = height * 0.15f
            val moonRadius = 40f

            // Moon glow
            starPaint.color = Color.argb(50, 255, 255, 200)
            canvas.drawCircle(moonX, moonY, moonRadius * 2, starPaint)

            // Moon body
            starPaint.color = Color.parseColor("#F5F5DC")
            canvas.drawCircle(moonX, moonY, moonRadius, starPaint)

            // Moon phase shadow
            starPaint.color = Color.parseColor("#050810")
            val shadowOffset = moonRadius * (1 - moonPhase) * 2
            canvas.drawCircle(moonX + shadowOffset, moonY, moonRadius - 2, starPaint)

            // Sleep hours text
            starPaint.color = Color.WHITE
            starPaint.textSize = 20f
            starPaint.textAlign = Paint.Align.CENTER
            canvas.drawText("${sleepHours}h", moonX, moonY + moonRadius + 30, starPaint)
        }

        private fun drawShootingStars(canvas: Canvas, width: Int, height: Int) {
            val score = currentSnapshot.wellbeingScore
            if (score > 0.8f && time % 5 < 0.1f) {
                val startX = 0f
                val startY = (Math.random() * height * 0.3).toFloat()
                val endX = width * 0.3f
                val endY = startY + height * 0.1f

                starPaint.color = Color.WHITE
                starPaint.strokeWidth = 3f
                starPaint.style = Paint.Style.STROKE

                val path = android.graphics.Path()
                path.moveTo(startX, startY)
                path.lineTo(endX, endY)
                canvas.drawPath(path, starPaint)

                // Glow
                starPaint.color = Color.argb(100, 100, 150, 255)
                starPaint.strokeWidth = 8f
                canvas.drawPath(path, starPaint)
            }
        }

        data class Star(
            var x: Float,
            var y: Float,
            var size: Float,
            var brightness: Float
        )
    }
}