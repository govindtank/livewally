package com.example.livewally.di;

import android.content.Context;
import com.example.livewally.data.repository.WellbeingRepositoryImpl;
import com.example.livewally.data.source.HealthConnectDataSource;
import com.example.livewally.data.source.NotificationDataSource;
import com.example.livewally.data.source.SensorDataSource;
import com.example.livewally.data.source.UsageStatsDataSource;
import com.example.livewally.domain.repository.WellbeingRepository;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/example/livewally/di/RepositoryModule;", "", "()V", "bindWellbeingRepository", "Lcom/example/livewally/domain/repository/WellbeingRepository;", "impl", "Lcom/example/livewally/data/repository/WellbeingRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.livewally.domain.repository.WellbeingRepository bindWellbeingRepository(@org.jetbrains.annotations.NotNull()
    com.example.livewally.data.repository.WellbeingRepositoryImpl impl);
}