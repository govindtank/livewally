package com.example.livewally.wallpaper.garden

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.example.livewally.data.model.WellbeingSnapshot
import com.example.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.cos
import kotlin.math.sin

class DigitalGardenWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = DigitalGardenWallpaperEngine()

    inner class DigitalGardenWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val plantPaint = Paint().apply { isAntiAlias = true }
        private val plants = mutableListOf<Plant>()

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            initializePlants(width, height)
        }

        private fun initializePlants(width: Int, height: Int) {
            plants.clear()
            val plantTypes = listOf("oak", "fern", "rose", "sunflower", "jasmine")
            for (i in 0..4) {
                plants.add(Plant(
                    x = width * (0.2f + i * 0.15f),
                    y = height * 0.8f,
                    type = plantTypes[i % plantTypes.size],
                    health = 0.5f + i * 0.1f
                ))
            }
        }

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.05f

            // Draw sky gradient
            val skyColor = when {
                currentSnapshot.wellbeingScore > 0.7f -> listOf(
                    Color.parseColor("#0a1a0d"),
                    Color.parseColor("#1a3a1f")
                )
                currentSnapshot.wellbeingScore > 0.4f -> listOf(
                    Color.parseColor("#1a1a0a"),
                    Color.parseColor("#2a2a15")
                )
                else -> listOf(
                    Color.parseColor("#0d0d0d"),
                    Color.parseColor("#1a1a1a")
                )
            }

            val gradient = android.graphics.LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                skyColor.toIntArray(), null,
                android.graphics.Shader.TileMode.CLAMP
            )
            plantPaint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), plantPaint)

            // Draw ground
            val groundColor = when {
                currentSnapshot.wellbeingScore > 0.7f -> Color.parseColor("#1a3a1f")
                currentSnapshot.wellbeingScore > 0.4f -> Color.parseColor("#2a2a15")
                else -> Color.parseColor("#1a1a1a")
            }
            plantPaint.color = groundColor
            canvas.drawRect(0f, height * 0.7f, width.toFloat(), height.toFloat(), plantPaint)

            // Draw plants
            plants.forEach { plant ->
                drawPlant(canvas, plant, width, height)
            }

            // Draw fireflies at night
            if (currentSnapshot.wellbeingScore < 0.5f) {
                drawFireflies(canvas, width, height)
            }
        }

        private fun drawPlant(canvas: Canvas, plant: Plant, width: Int, height: Int) {
            val growth = plant.health * (0.5f + 0.5f * sin(time + plant.x * 0.01f))
            val plantHeight = height * 0.3f * growth
            val sway = cos(time * 2 + plant.x * 0.01f) * 10 * growth

            // Stem
            plantPaint.color = Color.parseColor("#228B22")
            plantPaint.strokeWidth = 8f
            canvas.drawLine(
                plant.x + sway,
                plant.y,
                plant.x + sway * 0.5f,
                plant.y - plantHeight,
                plantPaint
            )

            // Leaves
            plantPaint.color = when (plant.type) {
                "oak" -> Color.parseColor("#228B22")
                "fern" -> Color.parseColor("#32CD32")
                "rose" -> Color.parseColor("#DC143C")
                "sunflower" -> Color.parseColor("#FFD700")
                else -> Color.parseColor("#FFFFFF")
            }

            for (i in 0..2) {
                val leafAngle = (i - 1) * 2f + sway * 0.1f
                val leafX = plant.x + sway * 0.5f + cos(leafAngle) * 20 * growth
                val leafY = plant.y - plantHeight * 0.5f + sin(leafAngle) * 20 * growth

                val leafPath = Path()
                leafPath.moveTo(plant.x + sway * 0.5f, plant.y - plantHeight * 0.5f)
                leafPath.quadTo(
                    leafX, leafY,
                    plant.x + sway * 0.5f + cos(leafAngle + 1f) * 30 * growth,
                    plant.y - plantHeight * 0.5f + sin(leafAngle + 1f) * 30 * growth
                )
                canvas.drawPath(leafPath, plantPaint)
            }

            // Flower/Bud
            if (growth > 0.7f) {
                plantPaint.color = when (plant.type) {
                    "rose" -> Color.parseColor("#FF1493")
                    "sunflower" -> Color.parseColor("#FFD700")
                    "jasmine" -> Color.parseColor("#FFFFF0")
                    else -> Color.parseColor("#FF69B4")
                }
                canvas.drawCircle(
                    plant.x + sway * 0.5f,
                    plant.y - plantHeight,
                    10 * growth,
                    plantPaint
                )
            }
        }

        private fun drawFireflies(canvas: Canvas, width: Int, height: Int) {
            for (i in 0..10) {
                val fx = (width * 0.1f + cos(time * 0.5f + i) * width * 0.8f)
                val fy = (height * 0.2f + sin(time * 0.3f + i * 2) * height * 0.5f)
                val alpha = (100 + sin(time + i) * 100).toInt()

                plantPaint.color = Color.argb(alpha, 255, 255, 200)
                canvas.drawCircle(fx, fy, 3f, plantPaint)
            }
        }

        data class Plant(
            var x: Float,
            var y: Float,
            val type: String,
            var health: Float
        )
    }
}