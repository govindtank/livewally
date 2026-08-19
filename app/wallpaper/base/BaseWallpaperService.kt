package com.droidtank.livewally.wallpaper.base

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.Bundle
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color as ComposeColor
import com.droidtank.livewally.data.model.WellbeingSnapshot

abstract class BaseWallpaperService : WallpaperService() {

    protected abstract fun createEngine(): BaseWallpaperEngine

    override fun onCreateEngine(): Engine = createEngine()

    abstract inner class BaseWallpaperEngine : Engine() {
        private var visible = false
        private val holder: SurfaceHolder get() = surfaceHolder
        private val paint = Paint().apply {
            isAntiAlias = true
        }
        
        protected var currentSnapshot: WellbeingSnapshot = WellbeingSnapshot.default()
        protected var frameRate = 30
        protected var batterySaverMode = false
        
        private var drawThread: Thread? = null
        private var running = false

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            Log.d("BaseWallpaper", "Wallpaper engine created")
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            this.visible = visible
            if (visible) {
                startDrawing()
            } else {
                stopDrawing()
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            holder.setFormat(PixelFormat.RGBA_8888)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            stopDrawing()
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset)
            drawFrame()
        }

        protected open fun startDrawing() {
            if (running) return
            running = true
            drawThread = Thread {
                while (running) {
                    if (visible) {
                        drawFrame()
                    }
                    try {
                        val fps = if (batterySaverMode) 15 else frameRate
                        Thread.sleep(1000L / fps)
                    } catch (e: InterruptedException) {
                        break
                    }
                }
            }.apply { start() }
        }

        protected open fun stopDrawing() {
            running = false
            drawThread?.interrupt()
            drawThread = null
        }

        protected open fun drawFrame() {
            val canvas: Canvas? = try {
                holder.lockCanvas()
            } catch (e: Exception) {
                Log.e("BaseWallpaper", "Failed to lock canvas", e)
                return
            } ?: return

            try {
                // Clear canvas
                canvas.drawColor(Color.BLACK)
                
                // Draw wallpaper content
                onDraw(canvas, canvas.width, canvas.height)
            } finally {
                try {
                    holder.unlockCanvasAndPost(canvas)
                } catch (e: Exception) {
                    Log.e("BaseWallpaper", "Failed to unlock canvas", e)
                }
            }
        }

        protected abstract fun onDraw(canvas: Canvas, width: Int, height: Int)

        fun updateSnapshot(snapshot: WellbeingSnapshot) {
            currentSnapshot = snapshot
            if (visible) {
                drawFrame()
            }
        }

        fun updateSettings(frameRate: Int, batterySaverMode: Boolean) {
            this.frameRate = frameRate
            this.batterySaverMode = batterySaverMode
        }

        // Helper methods for drawing
        protected fun drawGradient(canvas: Canvas, colors: List<Int>, width: Int, height: Int) {
            val gradient = android.graphics.LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                colors.toIntArray(),
                null,
                android.graphics.Shader.TileMode.CLAMP
            )
            paint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }

        protected fun drawCircle(canvas: Canvas, cx: Float, cy: Float, radius: Float, color: Int) {
            paint.color = color
            paint.style = Paint.Style.FILL
            canvas.drawCircle(cx, cy, radius, paint)
        }

        protected fun drawText(canvas: Canvas, text: String, x: Float, y: Float, textSize: Float, color: Int) {
            paint.color = color
            paint.textSize = textSize
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(text, x, y, paint)
        }
    }
}