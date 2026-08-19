package com.droidtank.livewally.ui.wallpaper

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.droidtank.livewally.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sin

data class WallpaperInfo(
    val id: String,
    val name: String,
    val description: String,
    val gradient: List<Color>,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val wallpapers = listOf(
    WallpaperInfo(
        id = "breathing",
        name = "Breathing Aurora",
        description = "A living aurora that breathes with you. Colors shift based on screen time.",
        gradient = listOf(Color(0xFF1B6B4A), Color(0xFF0D9E75), Color(0xFF4ECDC4)),
        icon = Icons.Default.Air
    ),
    WallpaperInfo(
        id = "garden",
        name = "Digital Garden",
        description = "Watch your garden bloom as you use your phone mindfully.",
        gradient = listOf(Color(0xFF0a1a0d), Color(0xFF1a3a1f)),
        icon = Icons.Default.Park
    ),
    WallpaperInfo(
        id = "ocean",
        name = "Usage Ocean",
        description = "An ocean that reflects your digital habits. Calm waters = balanced usage.",
        gradient = listOf(Color(0xFF0a1a2e), Color(0xFF1a3a5c)),
        icon = Icons.Default.Water
    ),
    WallpaperInfo(
        id = "cosmos",
        name = "Cosmos Constellation",
        description = "Your wellbeing forms a living constellation across the night sky.",
        gradient = listOf(Color(0xFF050810), Color(0xFF1a1a3e)),
        icon = Icons.Default.AutoAwesome
    ),
    WallpaperInfo(
        id = "forest",
        name = "Mindful Forest",
        description = "A forest path that clears when you're present. Foggy when distracted.",
        gradient = listOf(Color(0xFF0d1a0a), Color(0xFF1a2d12)),
        icon = Icons.Default.Forest
    ),
    WallpaperInfo(
        id = "clock",
        name = "Wellbeing Clock",
        description = "A beautiful clock that encodes your daily wellbeing metrics.",
        gradient = listOf(Color(0xFF0D1117), Color(0xFF1D9E75)),
        icon = Icons.Default.Schedule
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperPickerScreen(
    viewModel: WallpaperPickerViewModel = hiltViewModel()
) {
    val selectedWallpaper by viewModel.selectedWallpaper.collectAsState()
    var showPreviewDialog by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Your Wallpaper",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Each wallpaper reflects your digital wellbeing in a unique way.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Horizontal scroll of preview cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(wallpapers) { wallpaper ->
                WallpaperPreviewCard(
                    wallpaper = wallpaper,
                    isSelected = selectedWallpaper == wallpaper.id,
                    onClick = { showPreviewDialog = wallpaper.id }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "All Wallpapers",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        wallpapers.forEach { wallpaper ->
            WallpaperListItem(
                wallpaper = wallpaper,
                isSelected = selectedWallpaper == wallpaper.id,
                onClick = { viewModel.selectWallpaper(wallpaper.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    // Preview Dialog
    showPreviewDialog?.let { wallpaperId ->
        val wallpaper = wallpapers.find { it.id == wallpaperId }
        wallpaper?.let {
            PreviewDialog(
                wallpaper = it,
                onDismiss = { showPreviewDialog = null },
                onSetWallpaper = {
                    viewModel.selectWallpaper(it.id)
                    showPreviewDialog = null
                }
            )
        }
    }
}

@Composable
fun WallpaperPreviewCard(
    wallpaper: WallpaperInfo,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Animation for pulsing effect
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Animated gradient shift
    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(280.dp)
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(
                    3.dp,
                    AccentTeal,
                    RoundedCornerShape(12.dp)
                ) else Modifier
            )
            .graphicsLayer {
                scaleX = pulseScale
                scaleY = pulseScale
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Animated gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = wallpaper.gradient,
                            startY = -gradientShift * 100,
                            endY = 280f - gradientShift * 100
                        )
                    )
            )

            // Subtle overlay animation
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            center = androidx.compose.ui.geometry.Offset(
                                100f + gradientShift * 40,
                                140f + sin(gradientShift * Math.PI * 2).toFloat() * 20
                            ),
                            radius = 80f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon with animation
                Icon(
                    imageVector = wallpaper.icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier
                        .size(40.dp)
                        .graphicsLayer {
                            rotationZ = sin(gradientShift * Math.PI * 4).toFloat() * 5
                        }
                )

                // Info
                Column {
                    Text(
                        text = wallpaper.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = wallpaper.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 3
                    )
                }
            }

            // Selected indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AccentTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WallpaperListItem(
    wallpaper: WallpaperInfo,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AccentTeal.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.linearGradient(wallpaper.gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = wallpaper.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wallpaper.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = wallpaper.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = AccentTeal,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Select",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewDialog(
    wallpaper: WallpaperInfo,
    onDismiss: () -> Unit,
    onSetWallpaper: () -> Unit
) {
    // Animation for preview
    val infiniteTransition = rememberInfiniteTransition()
    val previewShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = wallpaper.name,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = wallpaper.gradient,
                                startY = -previewShift * 100,
                                endY = 200f - previewShift * 100
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = wallpaper.icon,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = wallpaper.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSetWallpaper,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentTeal
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Wallpaper,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Set as Wallpaper")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@HiltViewModel
class WallpaperPickerViewModel @Inject constructor(
    @ApplicationContext private val context: android.content.Context
) : ViewModel() {
    private val _selectedWallpaper = kotlinx.coroutines.flow.MutableStateFlow("breathing")
    val selectedWallpaper = _selectedWallpaper

    fun selectWallpaper(id: String) {
        _selectedWallpaper.value = id
        // Set the wallpaper
        kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val wallpaperInfo = wallpapers.find { it.id == id }
                wallpaperInfo?.let {
                    val bitmap = createGradientBitmap(it.gradient)
                    wallpaperManager.setBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createGradientBitmap(colors: List<Color>): Bitmap {
        val width = 1080  // Standard wallpaper width
        val height = 1920 // Standard wallpaper height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = AndroidCanvas(bitmap)
        val paint = Paint()
        val shader = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            colors.map { it.toArgb() }.toIntArray(),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }
}
