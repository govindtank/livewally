package com.example.livewally.util;

import android.app.NotificationManager;
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
public final class BedtimeModeManager_Factory implements Factory<BedtimeModeManager> {
  private final Provider<Context> contextProvider;

  private final Provider<NotificationManager> notificationManagerProvider;

  public BedtimeModeManager_Factory(Provider<Context> contextProvider,
      Provider<NotificationManager> notificationManagerProvider) {
    this.contextProvider = contextProvider;
    this.notificationManagerProvider = notificationManagerProvider;
  }

  @Override
  public BedtimeModeManager get() {
    return newInstance(contextProvider.get(), notificationManagerProvider.get());
  }

  public static BedtimeModeManager_Factory create(Provider<Context> contextProvider,
      Provider<NotificationManager> notificationManagerProvider) {
    return new BedtimeModeManager_Factory(contextProvider, notificationManagerProvider);
  }

  public static BedtimeModeManager newInstance(Context context,
      NotificationManager notificationManager) {
    return new BedtimeModeManager(context, notificationManager);
  }
}
