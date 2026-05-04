package com.example.livewally.ui.wallpaper;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import com.example.livewally.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import android.graphics.Canvas;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a,\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u0007\u001a&\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u0007\u001a\u0012\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001a&\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u0007\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0013"}, d2 = {"wallpapers", "", "Lcom/example/livewally/ui/wallpaper/WallpaperInfo;", "getWallpapers", "()Ljava/util/List;", "PreviewDialog", "", "wallpaper", "onDismiss", "Lkotlin/Function0;", "onSetWallpaper", "WallpaperListItem", "isSelected", "", "onClick", "WallpaperPickerScreen", "viewModel", "Lcom/example/livewally/ui/wallpaper/WallpaperPickerViewModel;", "WallpaperPreviewCard", "app_debug"})
public final class WallpaperPickerScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.example.livewally.ui.wallpaper.WallpaperInfo> wallpapers = null;
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.example.livewally.ui.wallpaper.WallpaperInfo> getWallpapers() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void WallpaperPickerScreen(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.wallpaper.WallpaperPickerViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WallpaperPreviewCard(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.wallpaper.WallpaperInfo wallpaper, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WallpaperListItem(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.wallpaper.WallpaperInfo wallpaper, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PreviewDialog(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.wallpaper.WallpaperInfo wallpaper, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSetWallpaper) {
    }
}