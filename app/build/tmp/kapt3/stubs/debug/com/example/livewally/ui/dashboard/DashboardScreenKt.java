package com.example.livewally.ui.dashboard;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import com.example.livewally.data.model.WellbeingSnapshot;
import com.example.livewally.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharingStarted;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u001a2\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\b\u0010\u000f\u001a\u00020\u0001H\u0007\u001a<\u0010\u0010\u001a\u00020\u00012\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u001a\u0010\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001e"}, d2 = {"DashboardScreen", "", "viewModel", "Lcom/example/livewally/ui/dashboard/DashboardViewModel;", "GardenPlantIndicator", "name", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "isBlooming", "", "GardenPlantIndicator-9LQNqLg", "(Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JZ)V", "GardenPreviewCard", "MetricCard", "modifier", "Landroidx/compose/ui/Modifier;", "title", "value", "MetricCard-xwkQ0AY", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "ScreenTimeChartCard", "WellbeingScoreCard", "snapshot", "Lcom/example/livewally/data/model/WellbeingSnapshot;", "getWellbeingMessage", "score", "", "app_debug"})
public final class DashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DashboardScreen(@org.jetbrains.annotations.NotNull()
    com.example.livewally.ui.dashboard.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WellbeingScoreCard(@org.jetbrains.annotations.NotNull()
    com.example.livewally.data.model.WellbeingSnapshot snapshot) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ScreenTimeChartCard() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void GardenPreviewCard() {
    }
    
    private static final java.lang.String getWellbeingMessage(float score) {
        return null;
    }
}