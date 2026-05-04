# LiveWally - Digital Wellbeing Live Wallpaper

A production-quality Android live wallpaper app that transforms your home screen into a reflection of your digital wellbeing. Built with Kotlin, MVVM + Clean Architecture, Jetpack Compose, and Hilt.

## Features

### 6 Animated Live Wallpapers

1. **Breathing Aurora** - A living aurora that breathes with you. Colors shift based on screen time:
   - Teal-green: Healthy usage (<2h)
   - Amber: Moderate usage (2-4h)
   - Rose-red: Heavy usage (>4h)

2. **Digital Garden** - Your phone becomes a living garden:
   - Oak (Productivity): Blooms with focused work
   - Dandelion (Social): Blooms with low social media usage
   - Sunflower (Activity): Blooms with steps
   - Jasmine (Sleep): Blooms with good sleep
   - Fireflies appear at night

3. **Usage Ocean** - A deep ocean that mirrors your habits:
   - Tide height reflects screen time
   - Wave amplitude based on notifications
   - Storm clouds when overwhelmed
   - Fish scatter during unlock spikes

4. **Cosmos Constellation** - Your wellbeing forms a night sky:
   - Stars represent 7-day history
   - Constellation lines connect good days
   - Shooting stars for personal bests
   - Moon phase reflects sleep debt

5. **Mindful Forest** - A forest path that clears when present:
   - Fog density based on screen time
   - Light rays appear with high wellbeing
   - Birds fly with activity
   - Leaves fall at bedtime

6. **Wellbeing Clock** - A practical clock wallpaper:
   - 24-segment ring shows hourly screen time
   - Step arc like watch activity ring
   - Sleep indicator moon
   - Mood-colored hands

## Architecture

```
app/
├── data/           # Data layer
│   ├── local/      # Room DB, DataStore
│   ├── repository/  # Repository implementations
│   └── source/     # Health Connect, Usage Stats, Sensors
├── domain/          # Business logic
│   ├── model/       # WellbeingSnapshot, enums
│   ├── repository/  # Repository interfaces
│   └── usecase/    # Use cases
├── wallpaper/      # Live wallpaper services
│   ├── base/       # BaseWallpaperService, BaseWallpaperEngine
│   ├── breath/     # Breathing Aurora
│   ├── garden/     # Digital Garden
│   ├── ocean/      # Usage Ocean
│   ├── cosmos/     # Cosmos Constellation
│   ├── forest/     # Mindful Forest
│   └── clock/      # Wellbeing Clock
├── ui/             # Compose UI
│   ├── dashboard/  # Main wellbeing dashboard
│   ├── wallpaper/  # Wallpaper picker
│   └── settings/   # App settings
└── di/             # Hilt modules
```

## Permissions

### Required Permissions

| Permission | Purpose |
|------------|---------|
| `PACKAGE_USAGE_STATS` | Track daily screen time and app usage |
| `BIND_NOTIFICATION_LISTENER_SERVICE` | Count notifications |
| `POST_NOTIFICATIONS` | Notify about wellbeing milestones |

### Optional Permissions

| Permission | Purpose |
|------------|---------|
| `health.READ_STEPS` | Read step count from Health Connect |
| `health.READ_SLEEP` | Read sleep data from Health Connect |
| `health.READ_HEART_RATE` | Read heart rate from Health Connect |
| `ACTIVITY_RECOGNITION` | Fallback step counting |
| `ACCESS_NOTIFICATION_POLICY` | Enable Zen Device Effects (Android 15) |

## Setup Instructions

### 1. Clone and Build

```bash
git clone <repository-url>
cd livewally
./gradlew assembleDebug
```

### 2. Install

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 3. Grant Permissions

1. **Usage Stats**: Settings → Apps → LiveWally → Usage access
2. **Notification Access**: Settings → Apps → LiveWally → Notification access
3. **Health Connect**: Settings → Apps → LiveWally → Connected apps

### 4. Set Wallpaper

1. Open the app
2. Go to "Wallpapers" tab
3. Tap a wallpaper to preview
4. Tap "Set as Wallpaper"

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Async**: Kotlin Coroutines + Flow
- **UI**: Jetpack Compose with Material Design 3
- **Drawing**: Canvas API (no OpenGL)
- **Background**: WorkManager
- **Storage**: DataStore Preferences + Room
- **Build**: Gradle Kotlin DSL
- **Target SDK**: 35 (Android 15)
- **Min SDK**: 26 (Android 8.0)

## Wellbeing Score Algorithm

```kotlin
val screenScore = 1f - (screenTimeMinutes / 480f).coerceIn(0f, 1f)
val stepScore = (stepCount / 8000f).coerceIn(0f, 1f)
val sleepScore = when {
    sleepHours in 7.0..9.0 -> 1f
    sleepHours in 6.0..7.0 -> 0.6f
    sleepHours < 6.0 -> 0.2f
    else -> 0.4f
}
val notifScore = 1f - (notificationCount / 200f).coerceIn(0f, 1f)

wellbeingScore = screenScore * 0.35f + stepScore * 0.25f + 
                 sleepScore * 0.25f + notifScore * 0.15f
```

## Performance

- Target: 30fps on mid-range devices (Snapdragon 6-series)
- Flagship: 60fps
- Battery saver: Halves frame rate when battery < 20%
- Stops all animation when wallpaper is invisible

## License

MIT License - See LICENSE file for details.
