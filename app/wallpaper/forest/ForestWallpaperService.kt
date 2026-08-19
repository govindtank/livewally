package com.droidtank.livewally.wallpaper.forest

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.wallpaper.base.BaseWallpaperService
import kotlin.math.cos
import kotlin.math.sin

class ForestWallpaperService : BaseWallpaperService() {
    override fun createEngine(): BaseWallpaperEngine = ForestWallpaperEngine()

    inner class ForestWallpaperEngine : BaseWallpaperEngine() {
        private var time = 0f
        private val treePaint = Paint().apply { isAntiAlias = true }
        private val trees = mutableListOf<Tree>()
        private var birds = mutableListOf<Bird>()

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            initializeForest(width, height)
        }

        private fun initializeForest(width: Int, height: Int) {
            trees.clear()
            birds.clear()
            for (i in 0..8) {
                trees.add(Tree(
                    x = width * (0.1f + i * 0.11f),
                    y = height * 0.7f,
                    height = height * (0.2f + Math.random().toFloat() * 0.2f),
                    type = if (i % 3 == 0) "pine" else "oak"
                ))
            }
            for (i in 0..5) {
                birds.add(Bird(
                    x = width * (0.2f + Math.random() * 0.6f),
                    y = height * (0.1f + Math.random() * 0.3f),
                    speed = 1f + Math.random().toFloat() * 2f
                ))
            }
        }

        override fun onDraw(canvas: Canvas, width: Int, height: Int) {
            time += 0.03f

            val score = currentSnapshot.wellbeingScore
            val isNight = score < 0.4f

            // Draw sky
            drawSky(canvas, width, height, score, isNight)

            // Draw ground
            drawGround(canvas, width, height, score)

            // Draw trees
            trees.forEach { tree ->
                drawTree(canvas, tree, width, height, score, isNight)
            }

            // Draw fog
            if (score < 0.5f) {
                drawFog(canvas, width, height, score)
            }

            // Draw birds
            if (score > 0.5f) {
                drawBirds(canvas, width, height, score)
            }

            // Draw leaves falling
            if (score > 0.6f) {
                drawFallingLeaves(canvas, width, height)
            }
        }

        private fun drawSky(canvas: Canvas, width: Int, height: Int, score: Float, isNight: Boolean) {
            val colors = if (isNight) {
                listOf(
                    Color.parseColor("#0a0a1a"),
                    Color.parseColor("#1a1a2a")
                )
            } else {
                listOf(
                    Color.parseColor("#1a2a1a"),
                    Color.parseColor("#2a3a2a")
                )
            }

            val gradient = LinearGradient(
                0f, 0f, 0f, height * 0.6f,
                colors.toIntArray(), null,
                Shader.TileMode.CLAMP
            )
            treePaint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), treePaint)

            // Draw stars at night
            if (isNight) {
                treePaint.color = Color.argb(150, 255, 255, 255)
                for (i in 0..30) {
                    val sx = (Math.random() * width).toFloat()
                    val sy = (Math.random() * height * 0.5).toFloat()
                    val twinkle = (sin(time * 2 + i) * 0.5f + 0.5f)
                    canvas.drawCircle(sx, sy, 1f, treePaint.apply { alpha = (150 * twinkle).toInt() })
                }
            }
        }

        private fun drawGround(canvas: Canvas, width: Int, height: Int, score: Float) {
            val groundColor = when {
                score > 0.7f -> Color.parseColor("#1a3a1a")
                score > 0.4f -> Color.parseColor("#2a2a1a")
                else -> Color.parseColor("#1a1a1a")
            }
            treePaint.color = groundColor
            canvas.drawRect(0f, height * 0.6f, width.toFloat(), height.toFloat(), treePaint)
        }

        private fun drawTree(canvas: Canvas, tree: Tree, width: Int, height: Int, score: Float, isNight: Boolean) {
            val sway = cos(time * 2 + tree.x * 0.01f) * 5 * (score * 0.5f + 0.5f)

            // Trunk
            treePaint.color = Color.parseColor("#8B4513")
            treePaint.strokeWidth = tree.height * 0.1f
            treePaint.style = Paint.Style.STROKE
            canvas.drawLine(
                tree.x + sway,
                tree.y,
                tree.x + sway,
                tree.y - tree.height,
                treePaint
            )

            // Foliage
            val foliageColor = when {
                isNight -> Color.parseColor("#0a1a0a")
                score > 0.7f -> Color.parseColor("#228B22")
                score > 0.4f -> Color.parseColor("#32CD32")
                else -> Color.parseColor("#2a2a1a")
            }
            treePaint.color = foliageColor
            treePaint.style = Paint.Style.FILL

            if (tree.type == "pine") {
                // Pine tree - triangular shape
                val path = android.graphics.Path()
                path.moveTo(tree.x + sway, tree.y - tree.height)
                path.lineTo(tree.x + sway - tree.height * 0.3f, tree.y - tree.height * 0.4f)
                path.lineTo(tree.x + sway + tree.height * 0.3f, tree.y - tree.height * 0.4f)
                path.close()
                canvas.drawPath(path, treePaint)
            } else {
                // Oak tree - rounded canopy
                canvas.drawCircle(
                    tree.x + sway,
                    tree.y - tree.height * 0.5f,
                    tree.height * 0.4f,
                    treePaint
                )
            }
        }

        private fun drawFog(canvas: Canvas, width: Int, height: Int, score: Float) {
            val fogIntensity = (0.5f - score) * 2f
            treePaint.color = Color.argb((fogIntensity * 80).toInt(), 200, 200, 200)
            
            for (i in 0..3) {
                val fogY = height * (0.3f + i * 0.1f)
                val fogX = (time * 10 + i * 100) % (width + 200)
                val fogWidth = 200f + sin(time + i) * 50
                
                treePaint.alpha = (fogIntensity * 60).toInt()
                canvas.drawCircle(fogX, fogY, fogWidth, treePaint)
            }
        }

        private fun drawBirds(canvas: Canvas, width: Int, height: Int, score: Float) {
            birds.forEach { bird ->
                bird.x += bird.speed * (1 + score * 0.5f)
                if (bird.x > width + 50) {
                    bird.x = -50f
                    bird.y = height * (0.1f + Math.random().toFloat() * 0.2f)
                }

                val birdYOffset = sin(time * 3 + bird.x * 0.05f) * 10
                drawBird(canvas, bird.x, bird.y + birdYOffset, score)
            }
        }

        private fun drawBird(canvas: Canvas, x: Float, y: Float, score: Float) {
            treePaint.color = Color.BLACK
            treePaint.strokeWidth = 2f
            treePaint.style = Paint.Style.STROKE

            // Simple bird shape - V formation
            val wingFlap = sin(time * 5) * 5
            canvas.drawLine(x - 10, y, x, y + wingFlap, treePaint)
            canvas.drawLine(x, y + wingFlap, x + 10, y, treePaint)
        }

        private fun drawFallingLeaves(canvas: Canvas, width: Int, height: Int) {
            val leafCount = 5
            for (i in 0 until leafCount) {
                val leafX = (time * 20 + i * width / leafCount) % width
                val leafY = (time * 50 + i * 100) % height
                val leafSize = 5f + sin(time + i) * 2f

                treePaint.color = when (i % 3) {
                    0 -> Color.parseColor("#FF6347")
                    1 -> Color.parseColor("#FFD700")
                    else -> Color.parseColor("#DC143C")
                }
                treePaint.style = Paint.Style.FILL

                canvas.drawCircle(leafX, leafY, leafSize, treePaint)
            }
        }

        data class Tree(
            var x: Float,
            var y: Float,
            var height: Float,
            val type: String
        )

        data class Bird(
            var x: Float,
            var y: Float,
            val speed: Float
        )
    }
}