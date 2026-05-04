package com.example.livewally.data.repository;

import android.content.Context;
import com.example.livewally.data.source.HealthConnectDataSource;
import com.example.livewally.data.source.NotificationDataSource;
import com.example.livewally.data.source.SensorDataSource;
import com.example.livewally.data.source.UsageStatsDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WellbeingRepositoryImpl_Factory implements Factory<WellbeingRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<UsageStatsDataSource> usageStatsDataSourceProvider;

  private final Provider<HealthConnectDataSource> healthConnectDataSourceProvider;

  private final Provider<NotificationDataSource> notificationDataSourceProvider;

  private final Provider<SensorDataSource> sensorDataSourceProvider;

  public WellbeingRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<UsageStatsDataSource> usageStatsDataSourceProvider,
      Provider<HealthConnectDataSource> healthConnectDataSourceProvider,
      Provider<NotificationDataSource> notificationDataSourceProvider,
      Provider<SensorDataSource> sensorDataSourceProvider) {
    this.contextProvider = contextProvider;
    this.usageStatsDataSourceProvider = usageStatsDataSourceProvider;
    this.healthConnectDataSourceProvider = healthConnectDataSourceProvider;
    this.notificationDataSourceProvider = notificationDataSourceProvider;
    this.sensorDataSourceProvider = sensorDataSourceProvider;
  }

  @Override
  public WellbeingRepositoryImpl get() {
    return newInstance(contextProvider.get(), usageStatsDataSourceProvider.get(), healthConnectDataSourceProvider.get(), notificationDataSourceProvider.get(), sensorDataSourceProvider.get());
  }

  public static WellbeingRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<UsageStatsDataSource> usageStatsDataSourceProvider,
      Provider<HealthConnectDataSource> healthConnectDataSourceProvider,
      Provider<NotificationDataSource> notificationDataSourceProvider,
      Provider<SensorDataSource> sensorDataSourceProvider) {
    return new WellbeingRepositoryImpl_Factory(contextProvider, usageStatsDataSourceProvider, healthConnectDataSourceProvider, notificationDataSourceProvider, sensorDataSourceProvider);
  }

  public static WellbeingRepositoryImpl newInstance(Context context,
      UsageStatsDataSource usageStatsDataSource, HealthConnectDataSource healthConnectDataSource,
      NotificationDataSource notificationDataSource, SensorDataSource sensorDataSource) {
    return new WellbeingRepositoryImpl(context, usageStatsDataSource, healthConnectDataSource, notificationDataSource, sensorDataSource);
  }
}
