# LiveWally Privacy Policy

Last updated: August 19, 2026

## Introduction

LiveWally ("we", "our", or "us") is committed to protecting your privacy. This Privacy Policy explains how we collect, use, and safeguard your information when you use our Android application.

## Information We Collect

### Usage Data
- **Screen time and app usage**: Collected via the Usage Stats API to power wellbeing scores and wallpaper visuals
- **Activity and steps**: Collected via Health Connect or Activity Recognition API to track physical activity
- **Sleep data**: Collected via Health Connect to inform wellbeing metrics

### Notifications
- **Notification data**: Collected via Notification Listener Service to show notification-related wallpaper effects
- We do not read notification contents beyond count and category metadata for visual effects

### Device Data
- **Device information**: Basic device specs to optimize rendering performance
- **Battery state**: To implement battery-saver mode and adjust animation frame rates

## How We Use Your Information

- **Live wallpaper generation**: All collected data is processed locally on your device to generate animated wallpaper visuals
- **Wellbeing dashboard**: Data is combined to show your digital wellbeing score and trends
- **App optimization**: Battery and device data helps optimize performance

## Data Storage

- **All data is stored locally on your device** using DataStore Preferences and Room database
- **No data is sent to external servers**
- **No account creation is required**
- **No personal identifiers are collected or transmitted**

## Data Sharing

We do **not** share, sell, or rent any personal information to third parties. All processing happens locally on your device.

## Permissions Explanation

| Permission | Why We Need It | What We Access |
|------------|----------------|----------------|
| `PACKAGE_USAGE_STATS` | Track screen time for wellbeing metrics | App usage durations only |
| `BIND_NOTIFICATION_LISTENER_SERVICE` | Count notifications for wallpaper effects | Notification count and category only |
| `POST_NOTIFICATIONS` | Send wellbeing milestone alerts | Send local notifications only |
| Health Connect permissions | Read steps, sleep, heart rate | Health metrics for wellbeing score |
| `ACTIVITY_RECOGNITION` | Fallback step counting | Step counts only |
| `BODY_SENSORS` | Heart rate data (if available) | Heart rate only |
| `SET_WALLPAPER` | Apply live wallpapers | Set wallpaper only |
| `SCHEDULE_EXACT_ALARM` | Schedule wellbeing checks | Alarm scheduling only |

## Your Rights

- You can revoke any permission at any time via Android Settings
- You can uninstall the app at any time to permanently remove all data
- No account deletion is needed (we don't create accounts)

## Children's Privacy

LiveWally does not knowingly collect information from children under 13. The app is designed for general audiences and does not contain age-restricted content.

## Changes to This Policy

We may update this privacy policy from time to time. We will notify you of any changes by updating the "Last updated" date in this document.

## Contact Us

If you have questions about this privacy policy, please contact us at:

- **Email**: privacy@govindtank.com
- **Website**: https://govindtank.com

## Google Play Families Policy

LiveWally complies with the Google Play Families Policy. The app:
- Does not collect personal information from children
- Does not serve behavioral advertising
- Does not link to external websites or social media from child-directed features
- Uses only permitted ad SDKs (no ads in the app)
