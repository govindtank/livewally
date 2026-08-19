package com.droidtank.livewally.di

import android.content.Context
import com.droidtank.livewally.data.repository.WellbeingRepositoryImpl
import com.droidtank.livewally.data.source.HealthConnectDataSource
import com.droidtank.livewally.data.source.NotificationDataSource
import com.droidtank.livewally.data.source.SensorDataSource
import com.droidtank.livewally.data.source.UsageStatsDataSource
import com.droidtank.livewally.domain.repository.WellbeingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUsageStatsDataSource(
        @ApplicationContext context: Context
    ): UsageStatsDataSource = UsageStatsDataSource(context)

    @Provides
    @Singleton
    fun provideHealthConnectDataSource(
        @ApplicationContext context: Context
    ): HealthConnectDataSource = HealthConnectDataSource(context)

    @Provides
    @Singleton
    fun provideNotificationDataSource(
        @ApplicationContext context: Context
    ): NotificationDataSource = NotificationDataSource(context)

    @Provides
    @Singleton
    fun provideSensorDataSource(
        @ApplicationContext context: Context
    ): SensorDataSource = SensorDataSource(context)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWellbeingRepository(
        impl: WellbeingRepositoryImpl
    ): WellbeingRepository
}