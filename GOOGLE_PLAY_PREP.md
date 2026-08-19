# Google Play Store Submission Checklist

Use this checklist to prepare your LiveWally app for Google Play Store submission.

## Pre-Submission

- [ ] Create Google Play Developer Account ($25 one-time fee)
- [ ] Verify your identity with Google
- [ ] Choose app name: **LiveWally**
- [ ] Choose package name: `com.example.livewally` (or your custom domain)
- [ ] Prepare app logo (512x512 PNG)
- [ ] Prepare feature graphic (1024x500 PNG/JPG)
- [ ] Prepare at least 2 screenshots (phone, min 320px, max 3840px)
- [ ] Prepare short description (80 characters max)
- [ ] Prepare full description (4000 characters max)
- [ ] Add privacy policy URL (host PRIVACY_POLICY.md on your website)
- [ ] Set app category: **Personalization** or **Lifestyle**
- [ ] Set content rating: **Everyone**
- [ ] Set target audience: **Age 13+**

## Build Setup

- [ ] Update `applicationId` in `app/build.gradle` to your package name
- [ ] Generate release keystore
- [ ] Configure signing in `signing/signing-config.properties`
- [ ] Build release APK or App Bundle: `./gradlew assembleRelease` or `./gradlew bundleRelease`
- [ ] Test release build on device
- [ ] Run `./gradlew lint` and fix critical issues

## Google Play Console Steps

### App Content
- [ ] Privacy policy URL
- [ ] Data safety form (all data is collected locally, no transmission)
- [ ] App access (permissions explanation)
- [ ] Ads declaration (no ads in app)

### Store Listing
- [ ] App name: LiveWally
- [ ] Short description
- [ ] Full description
- [ ] Screenshots (phone, tablet, wear if applicable)
- [ ] Feature graphic
- [ ] App icon
- [ ] Category: Personalization
- [ ] Contact email
- [ ] Privacy policy URL
- [ ] Terms of service URL (optional)

### Release Management
- [ ] Create production release
- [ ] Upload App Bundle or APK
- [ ] Add release name and notes
- [ ] Review and roll out to production
- [ ] Set rollout percentage (start with 10% for testing)

## Post-Submission

- [ ] Monitor Play Console for review status
- [ ] Address any policy violations
- [ ] Respond to user reviews
- [ ] Update app regularly

## Important Notes

### Privacy Policy
Host the `PRIVACY_POLICY.md` on your website. Use a URL like:
`https://govindtank.com/livewally/privacy-policy`

### Data Safety
In the Play Console Data Safety form:
- **Data collection**: Yes, usage data is collected locally
- **Data sharing**: No, data is not shared
- **Data security**: Yes, data is encrypted locally
- **Data deletion**: Yes, uninstalling the app deletes all data

### Target SDK
- Current target SDK: 35 (Android 15)
- Update annually when new SDK versions are released

### Permissions Justification
Be prepared to justify each permission in the Play Console:
- `PACKAGE_USAGE_STATS`: Core feature for wellbeing metrics
- `BIND_NOTIFICATION_LISTENER_SERVICE`: Notification count for visual effects
- `POST_NOTIFICATIONS`: Local notifications for milestones
- Health Connect: Optional fitness integration
- `SET_WALLPAPER`: Core functionality

## Compliance

- [ ] Google Play Families Policy compliance
- [ ] Data Safety section completed accurately
- [ ] Privacy policy is accessible and complete
- [ ] No prohibited content or behavior
- [ ] No copyright violations (all assets are original or licensed)

## Rollout Strategy

1. **Internal testing**: Upload to internal test track
2. **Closed testing**: Invite 100-1000 testers
3. **Open testing**: Release to open testing track
4. **Production**: Full rollout after successful testing

## Monitoring

- [ ] Set up Play Console alerts
- [ ] Monitor crash reports
- [ ] Track user reviews and ratings
- [ ] Monitor install/uninstall rates
- [ ] Check policy compliance regularly
