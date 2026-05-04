package com.example.livewally.ui.settings;

import android.content.Context;
import com.example.livewally.data.store.SettingsDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public SettingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.contextProvider = contextProvider;
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(contextProvider.get(), settingsDataStoreProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new SettingsViewModel_Factory(contextProvider, settingsDataStoreProvider);
  }

  public static SettingsViewModel newInstance(Context context,
      SettingsDataStore settingsDataStore) {
    return new SettingsViewModel(context, settingsDataStore);
  }
}
