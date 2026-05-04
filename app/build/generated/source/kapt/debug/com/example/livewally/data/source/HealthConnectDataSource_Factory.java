package com.example.livewally.data.source;

import android.content.Context;
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
public final class HealthConnectDataSource_Factory implements Factory<HealthConnectDataSource> {
  private final Provider<Context> contextProvider;

  public HealthConnectDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HealthConnectDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static HealthConnectDataSource_Factory create(Provider<Context> contextProvider) {
    return new HealthConnectDataSource_Factory(contextProvider);
  }

  public static HealthConnectDataSource newInstance(Context context) {
    return new HealthConnectDataSource(context);
  }
}
