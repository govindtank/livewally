# LiveWally - Release Notes

## Version 1.0 (Initial Release)

### New Features
- 6 animated live wallpapers:
  - **Breathing Aurora** - Dynamic color shifting based on usage health
  - **Digital Garden** - Plants bloom with mindful usage
  - **Usage Ocean** - Tide and wave animations reflecting screen time
  - **Cosmos Constellation** - 7-day wellbeing history as a starry night sky
  - **Mindful Forest** - Dynamic forest scene with fog, light rays, and birds
  - **Wellbeing Clock** - Practical clock with wellbeing-aware styling

- **Dashboard** - Wellbeing score ring, metrics display, and garden preview
- **Wallpaper Picker** - Browse, preview, and apply wallpapers with one tap
- **Settings** - Permission guide, frame rate control, battery saver mode, goals

### Technical Details
- Built with Kotlin + Jetpack Compose + Material 3
- MVVM + Clean Architecture
- Hilt dependency injection
- WorkManager for background updates
- DataStore + Room for local storage
- Target SDK 35, Min SDK 30

### Known Limitations
- Health Connect requires device setup
- Some features require specific device sensors
- Wallpaper engine performance varies by device

## Planned Features
- Additional wallpaper themes
- Custom color schemes
- Widget support
- Backup and sync (local-only, no cloud)
