package com.example.livewally.ui.settings;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import com.example.livewally.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import android.content.Intent;
import android.provider.Settings;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.app.AppOpsManager;
import android.os.Process;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000d\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u0006H\u0007\u001a:\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\bH\u0007\u001a&\u0010\u0010\u001a\u00020\u00012\u001c\u0010\u0011\u001a\u0018\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0012\u00a2\u0006\u0002\b\u0014\u00a2\u0006\u0002\b\u0015H\u0007\u001aN\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u0012\u0010\u001e\u001a\u00020\u00012\b\b\u0002\u0010\u001f\u001a\u00020 H\u0007\u001a#\u0010!\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0011\u0010\u0011\u001a\r\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\u0014H\u0007\u001aR\u0010\"\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020$0&2\u0006\u0010\'\u001a\u00020(2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a<\u0010*\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u00062\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a4\u0010-\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00060"}, d2 = {"PermissionGuideItem", "", "title", "", "description", "granted", "", "onGrant", "Lkotlin/Function0;", "optional", "PermissionStatusItem", "status", "Lcom/example/livewally/ui/settings/PermissionStatus;", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "onAction", "SettingsCard", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SettingsGoalItem", "value", "color", "Landroidx/compose/ui/graphics/Color;", "onIncrease", "onDecrease", "SettingsGoalItem-Bx497Mc", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "SettingsScreen", "viewModel", "Lcom/example/livewally/ui/settings/SettingsViewModel;", "SettingsSection", "SettingsSliderItem", "subtitle", "", "valueRange", "Lkotlin/ranges/ClosedFloatingPointRange;", "steps", "", "onValueChange", "SettingsSwitchItem", "checked", "onCheckedChange", "SettingsTimePickerItem", "time", "onTimeChange", "app_debug"})
public final class SettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.settings.SettingsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PermissionGuideItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, boolean granted, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onGrant, boolean optional) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsSection(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsCard(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsSliderItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, float value, @org.jetbrains.annotations.NotNull()
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Float> valueRange, int steps, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onValueChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsSwitchItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean checked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsTimePickerItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String time, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTimeChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PermissionStatusItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.settings.PermissionStatus status, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAction) {
    }
}