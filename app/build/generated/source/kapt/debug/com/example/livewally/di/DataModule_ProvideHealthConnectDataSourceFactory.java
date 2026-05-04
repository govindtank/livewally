package com.example.livewally.di;

import android.content.Context;
import com.example.livewally.data.source.HealthConnectDataSource;
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
public final class DataModule_ProvideHealthConnectDataSourceFactory implements Factory<HealthConnectDataSource> {
  private final Provider<Context> contextProvider;

  public DataModule_ProvideHealthConnectDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HealthConnectDataSource get() {
    return provideHealthConnectDataSource(contextProvider.get());
  }

  public static DataModule_ProvideHealthConnectDataSourceFactory create(
      Provider<Context> contextProvider) {
    return new DataModule_ProvideHealthConnectDataSourceFactory(contextProvider);
  }

  public static HealthConnectDataSource provideHealthConnectDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideHealthConnectDataSource(context));
  }
}
