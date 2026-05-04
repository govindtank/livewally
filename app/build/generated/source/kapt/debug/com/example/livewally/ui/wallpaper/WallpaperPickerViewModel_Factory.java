package com.example.livewally.ui.wallpaper;

import android.content.Context;
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
public final class WallpaperPickerViewModel_Factory implements Factory<WallpaperPickerViewModel> {
  private final Provider<Context> contextProvider;

  public WallpaperPickerViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WallpaperPickerViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static WallpaperPickerViewModel_Factory create(Provider<Context> contextProvider) {
    return new WallpaperPickerViewModel_Factory(contextProvider);
  }

  public static WallpaperPickerViewModel newInstance(Context context) {
    return new WallpaperPickerViewModel(context);
  }
}
