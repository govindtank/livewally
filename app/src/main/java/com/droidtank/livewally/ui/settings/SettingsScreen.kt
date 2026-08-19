package com.droidtank.livewally.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.droidtank.livewally.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.content.Intent
import android.provider.Settings
import android.app.usage.UsageStatsManager
import android.content.Context
import android.app.AppOpsManager
import android.os.Process

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Permission Guidance Section
        SettingsSection(title = "Permission Setup Guide") {
            SettingsCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "To get the most out of LiveWally, please grant the following permissions:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    PermissionGuideItem(
                        title = "Usage Stats Access",
                        description = "Required to track screen time and app usage patterns. Without this, screen time tracking will not work.",
                        granted = uiState.usageStatsPermissionGranted,
                        onGrant = { viewModel.openUsageStatsSettings() }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    PermissionGuideItem(
                        title = "Notification Access",
                        description = "Optional - enables notification tracking to monitor digital habits.",
                        granted = false, // Will be checked by system
                        onGrant = { 
                            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        },
                        optional = true
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    PermissionGuideItem(
                        title = "Health Connect",
                        description = "Optional - provides accurate step count and sleep data from Health Connect.",
                        granted = false,
                        onGrant = null,
                        optional = true
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Wallpaper Behavior Section
        SettingsSection(title = "Wallpaper Behavior") {
            SettingsCard {
                SettingsSliderItem(
                    title = "Frame Rate",
                    subtitle = "${uiState.frameRate} FPS",
                    icon = Icons.Default.Speed,
                    value = uiState.frameRate.toFloat(),
                    valueRange = 15f..60f,
                    steps = 9,
                    onValueChange = { viewModel.setFrameRate(it.toInt()) }
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingsSwitchItem(
                    title = "Battery Saver Mode",
                    subtitle = "Halve frame rate when battery is low",
                    icon = Icons.Default.BatterySaver,
                    checked = uiState.batterySaverEnabled,
                    onCheckedChange = { viewModel.setBatterySaver(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Goals Section
        SettingsSection(title = "Goals") {
            SettingsCard {
                SettingsGoalItem(
                    title = "Step Goal",
                    value = "${uiState.stepGoal / 1000}k",
                    icon = Icons.Default.DirectionsWalk,
                    color = AccentTeal,
                    onIncrease = { viewModel.setStepGoal(uiState.stepGoal + 1000) },
                    onDecrease = { viewModel.setStepGoal((uiState.stepGoal - 1000).coerceAtLeast(1000)) }
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingsGoalItem(
                    title = "Screen Time Limit",
                    value = "${uiState.screenTimeLimit / 60}h",
                    icon = Icons.Default.PhoneAndroid,
                    color = AccentRose,
                    onIncrease = { viewModel.setScreenTimeLimit(uiState.screenTimeLimit + 30) },
                    onDecrease = { viewModel.setScreenTimeLimit((uiState.screenTimeLimit - 30).coerceAtLeast(30)) }
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp))

                SettingsGoalItem(
                    title = "Sleep Goal",
                    value = "${uiState.sleepGoal}h",
                    icon = Icons.Default.Bedtime,
                    color = AccentAmber,
                    onIncrease = { viewModel.setSleepGoal(uiState.sleepGoal + 0.5f) },
                    onDecrease = { viewModel.setSleepGoal((uiState.sleepGoal - 0.5f).coerceAtLeast(4f)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bedtime Mode Section
        SettingsSection(title = "Bedtime Mode") {
            SettingsCard {
                SettingsSwitchItem(
                    title = "Enable Bedtime Mode",
                    subtitle = "Dim wallpaper and enable Zen Mode",
                    icon = Icons.Default.Nightlight,
                    checked = uiState.bedtimeModeEnabled,
                    onCheckedChange = { viewModel.setBedtimeMode(it) }
                )

                if (uiState.bedtimeModeEnabled) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    SettingsTimePickerItem(
                        title = "Bedtime",
                        time = uiState.bedtimeTime,
                        icon = Icons.Default.Bed,
                        onTimeChange = { viewModel.setBedtimeTime(it) }
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    SettingsTimePickerItem(
                        title = "Wake Time",
                        time = uiState.wakeTime,
                        icon = Icons.Default.Alarm,
                        onTimeChange = { viewModel.setWakeTime(it) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PermissionGuideItem(
    title: String,
    description: String,
    granted: Boolean,
    onGrant: (() -> Unit)?,
    optional: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onGrant != null) Modifier.clickable { onGrant() } else Modifier
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when {
                granted -> Icons.Default.CheckCircle
                optional -> Icons.Default.Info
                else -> Icons.Default.Lock
            },
            contentDescription = null,
            tint = when {
                granted -> AccentTeal
                optional -> AccentAmber
                else -> AccentRose
            },
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        if (!granted && onGrant != null) {
            TextButton(onClick = onGrant) {
                Text(
                    text = if (optional) "Optional" else "Grant",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentTeal
                )
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsSliderItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AccentTeal,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.padding(start = 40.dp),
            colors = SliderDefaults.colors(
                thumbColor = AccentTeal,
                activeTrackColor = AccentTeal
            )
        )
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentTeal,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AccentTeal,
                checkedTrackColor = AccentTeal.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun SettingsGoalItem(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDecrease) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = color)
        }
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = "Increase", tint = color)
        }
    }
}

@Composable
fun SettingsTimePickerItem(
    title: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onTimeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentAmber,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = { /* Show time picker */ }) {
            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AccentAmber
            )
        }
    }
}

enum class PermissionStatus { GRANTED, REQUIRED, OPTIONAL }

@Composable
fun PermissionStatusItem(
    title: String,
    description: String,
    status: PermissionStatus,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onAction: (() -> Unit)? = null
) {
    val (statusColor, statusText) = when (status) {
        PermissionStatus.GRANTED -> AccentTeal to "Granted"
        PermissionStatus.REQUIRED -> AccentRose to "Required"
        PermissionStatus.OPTIONAL -> AccentAmber to "Optional"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onAction != null) { onAction?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        if (onAction != null) {
            TextButton(onClick = onAction) {
                Text(
                    text = "Grant",
                    style = MaterialTheme.typography.labelMedium,
                    color = AccentTeal
                )
            }
        }
    }
}
