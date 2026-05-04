package com.example.livewally.di;

import android.content.Context;
import com.example.livewally.data.source.UsageStatsDataSource;
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
public final class DataModule_ProvideUsageStatsDataSourceFactory implements Factory<UsageStatsDataSource> {
  private final Provider<Context> contextProvider;

  public DataModule_ProvideUsageStatsDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UsageStatsDataSource get() {
    return provideUsageStatsDataSource(contextProvider.get());
  }

  public static DataModule_ProvideUsageStatsDataSourceFactory create(
      Provider<Context> contextProvider) {
    return new DataModule_ProvideUsageStatsDataSourceFactory(contextProvider);
  }

  public static UsageStatsDataSource provideUsageStatsDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideUsageStatsDataSource(context));
  }
}
