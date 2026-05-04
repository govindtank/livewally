package com.example.livewally.di;

import android.content.Context;
import com.example.livewally.data.source.NotificationDataSource;
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
public final class DataModule_ProvideNotificationDataSourceFactory implements Factory<NotificationDataSource> {
  private final Provider<Context> contextProvider;

  public DataModule_ProvideNotificationDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationDataSource get() {
    return provideNotificationDataSource(contextProvider.get());
  }

  public static DataModule_ProvideNotificationDataSourceFactory create(
      Provider<Context> contextProvider) {
    return new DataModule_ProvideNotificationDataSourceFactory(contextProvider);
  }

  public static NotificationDataSource provideNotificationDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideNotificationDataSource(context));
  }
}
