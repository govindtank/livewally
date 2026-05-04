package com.example.livewally.di;

import android.content.Context;
import com.example.livewally.data.source.SensorDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DataModule_ProvideSensorDataSourceFactory implements Factory<SensorDataSource> {
  private final Provider<Context> contextProvider;

  public DataModule_ProvideSensorDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SensorDataSource get() {
    return provideSensorDataSource(contextProvider.get());
  }

  public static DataModule_ProvideSensorDataSourceFactory create(
      Provider<Context> contextProvider) {
    return new DataModule_ProvideSensorDataSourceFactory(contextProvider);
  }

  public static SensorDataSource provideSensorDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideSensorDataSource(context));
  }
}
