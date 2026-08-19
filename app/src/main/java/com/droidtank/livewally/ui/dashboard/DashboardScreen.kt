package com.droidtank.livewally.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: com.droidtank.livewally.domain.repository.WellbeingRepository
) : ViewModel() {
    val snapshot: StateFlow<WellbeingSnapshot> = repository.snapshot
    val isLoading: StateFlow<Boolean> = repository.isLoading
    val error: StateFlow<String?> = repository.error

    fun refresh() {
        viewModelScope.launch {
            repository.refresh()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val snapshot by viewModel.snapshot.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LiveWally") },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = "Refresh",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Error message
            error?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AccentRose.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Error, contentDescription = null, tint = AccentRose)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = it, style = MaterialTheme.typography.bodyMedium, color = AccentRose)
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = { viewModel.refresh() }, colors = ButtonDefaults.buttonColors(containerColor = AccentTeal)) {
                            Text("Retry")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Pull to refresh indicator
            if (isLoading && error == null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = AccentTeal
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Updating...", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Your Wellbeing",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Wellbeing Score Ring
            WellbeingScoreCard(snapshot)

            Spacer(modifier = Modifier.height(16.dp))

            // Metric Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Steps",
                    value = "${snapshot.stepCount / 1000}k",
                    icon = Icons.Default.DirectionsWalk,
                    color = AccentTeal
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Sleep",
                    value = "${snapshot.sleepHoursLast}h",
                    icon = Icons.Default.Bedtime,
                    color = AccentAmber
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Screen Time",
                    value = "${snapshot.screenTimeMinutes / 60}h",
                    icon = Icons.Default.PhoneAndroid,
                    color = AccentRose
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Notifications",
                    value = "${snapshot.notificationCount}",
                    icon = Icons.Default.Notifications,
                    color = Color(0xFF9C27B0)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Screen Time Chart
            ScreenTimeChartCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Garden Preview
            GardenPreviewCard()
        }
    }
}

@Composable
fun WellbeingScoreCard(snapshot: WellbeingSnapshot) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                val scoreColor = when {
                    snapshot.wellbeingScore > 0.7f -> AccentTeal
                    snapshot.wellbeingScore > 0.4f -> AccentAmber
                    else -> AccentRose
                }

                Canvas(modifier = Modifier.size(120.dp)) {
                    drawArc(
                        color = Color.Gray.copy(alpha = 0.2f),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round),
                        size = Size(size.width, size.height)
                    )
                    drawArc(
                        color = scoreColor,
                        startAngle = -90f,
                        sweepAngle = snapshot.wellbeingScore * 360f,
                        useCenter = false,
                        style = Stroke(width = 12f, cap = StrokeCap.Round),
                        size = Size(size.width, size.height)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(snapshot.wellbeingScore * 100).toInt()}",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = scoreColor
                    )
                    Text(
                        text = "Score",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = getWellbeingMessage(snapshot.wellbeingScore),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Top app: ${snapshot.topAppCategory.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = if (snapshot.isCharging) "Charging" else "On battery",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (snapshot.isCharging) AccentTeal else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun ScreenTimeChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Today's Screen Time",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                val barWidth = size.width / 24
                val maxHeight = size.height

                for (hour in 0 until 24) {
                    val height = (kotlin.math.sin(hour * 0.5) * 0.3 + 0.5).toFloat() * maxHeight
                    drawRect(
                        color = if (hour < java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY))
                            AccentTeal.copy(alpha = 0.8f)
                        else
                            AccentTeal.copy(alpha = 0.3f),
                        topLeft = Offset(hour * barWidth + 2, maxHeight - height),
                        size = Size(barWidth - 4, height)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0h", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text("12h", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text("Now", style = MaterialTheme.typography.bodySmall, color = AccentTeal)
            }
        }
    }
}

@Composable
fun GardenPreviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Your Garden Today",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GardenPlantIndicator("Oak", Icons.Default.Park, AccentTeal, true)
                GardenPlantIndicator("Fern", Icons.Default.Grass, AccentTeal, true)
                GardenPlantIndicator("Rose", Icons.Default.LocalFlorist, AccentRose, false)
            }
        }
    }
}

@Composable
fun GardenPlantIndicator(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, isBlooming: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isBlooming) color.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = if (isBlooming) color else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

private fun getWellbeingMessage(score: Float): String {
    return when {
        score > 0.8f -> "Excellent! You're thriving today!"
        score > 0.6f -> "Good day so far. Keep it up!"
        score > 0.4f -> "Balance is key. Take a moment."
        else -> "Consider some screen-free time."
    }
}
