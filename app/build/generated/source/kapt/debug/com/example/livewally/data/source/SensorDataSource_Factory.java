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
public final class SensorDataSource_Factory implements Factory<SensorDataSource> {
  private final Provider<Context> contextProvider;

  public SensorDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SensorDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static SensorDataSource_Factory create(Provider<Context> contextProvider) {
    return new SensorDataSource_Factory(contextProvider);
  }

  public static SensorDataSource newInstance(Context context) {
    return new SensorDataSource(context);
  }
}
