package com.example.livewally.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b#\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 62\u00020\u0001:\u00016Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\u0011H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\bH\u00c6\u0003J\u0010\u0010+\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\fH\u00c6\u0003J\t\u0010.\u001a\u00020\u000eH\u00c6\u0003J~\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u00100J\u0013\u00101\u001a\u00020\f2\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u00020\u0003H\u00d6\u0001J\t\u00104\u001a\u000205H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0014R\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001c\u00a8\u00067"}, d2 = {"Lcom/example/livewally/domain/model/WellbeingSnapshot;", "", "screenTimeMinutes", "", "unlockCount", "notificationCount", "stepCount", "sleepHoursLast", "", "heartRateAvg", "batteryPercent", "isCharging", "", "topAppCategory", "Lcom/example/livewally/domain/model/AppCategory;", "wellbeingScore", "timestamp", "", "(IIIIFLjava/lang/Integer;IZLcom/example/livewally/domain/model/AppCategory;FJ)V", "getBatteryPercent", "()I", "getHeartRateAvg", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "()Z", "getNotificationCount", "getScreenTimeMinutes", "getSleepHoursLast", "()F", "getStepCount", "getTimestamp", "()J", "getTopAppCategory", "()Lcom/example/livewally/domain/model/AppCategory;", "getUnlockCount", "getWellbeingScore", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IIIIFLjava/lang/Integer;IZLcom/example/livewally/domain/model/AppCategory;FJ)Lcom/example/livewally/domain/model/WellbeingSnapshot;", "equals", "other", "hashCode", "toString", "", "Companion", "app_debug"})
public final class WellbeingSnapshot {
    private final int screenTimeMinutes = 0;
    private final int unlockCount = 0;
    private final int notificationCount = 0;
    private final int stepCount = 0;
    private final float sleepHoursLast = 0.0F;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer heartRateAvg = null;
    private final int batteryPercent = 0;
    private final boolean isCharging = false;
    @org.jetbrains.annotations.NotNull()
    private final com.example.livewally.domain.model.AppCategory topAppCategory = null;
    private final float wellbeingScore = 0.0F;
    private final long timestamp = 0L;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.livewally.domain.model.WellbeingSnapshot.Companion Companion = null;
    
    public WellbeingSnapshot(int screenTimeMinutes, int unlockCount, int notificationCount, int stepCount, float sleepHoursLast, @org.jetbrains.annotations.Nullable()
    java.lang.Integer heartRateAvg, int batteryPercent, boolean isCharging, @org.jetbrains.annotations.NotNull()
    com.example.livewally.domain.model.AppCategory topAppCategory, float wellbeingScore, long timestamp) {
        super();
    }
    
    public final int getScreenTimeMinutes() {
        return 0;
    }
    
    public final int getUnlockCount() {
        return 0;
    }
    
    public final int getNotificationCount() {
        return 0;
    }
    
    public final int getStepCount() {
        return 0;
    }
    
    public final float getSleepHoursLast() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getHeartRateAvg() {
        return null;
    }
    
    public final int getBatteryPercent() {
        return 0;
    }
    
    public final boolean isCharging() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.livewally.domain.model.AppCategory getTopAppCategory() {
        return null;
    }
    
    public final float getWellbeingScore() {
        return 0.0F;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.livewally.domain.model.AppCategory component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.livewally.domain.model.WellbeingSnapshot copy(int screenTimeMinutes, int unlockCount, int notificationCount, int stepCount, float sleepHoursLast, @org.jetbrains.annotations.Nullable()
    java.lang.Integer heartRateAvg, int batteryPercent, boolean isCharging, @org.jetbrains.annotations.NotNull()
    com.example.livewally.domain.model.AppCategory topAppCategory, float wellbeingScore, long timestamp) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/livewally/domain/model/WellbeingSnapshot$Companion;", "", "()V", "default", "Lcom/example/livewally/domain/model/WellbeingSnapshot;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}