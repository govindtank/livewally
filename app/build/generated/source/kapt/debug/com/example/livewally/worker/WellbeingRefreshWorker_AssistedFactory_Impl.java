package com.example.livewally.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class WellbeingRefreshWorker_AssistedFactory_Impl implements WellbeingRefreshWorker_AssistedFactory {
  private final WellbeingRefreshWorker_Factory delegateFactory;

  WellbeingRefreshWorker_AssistedFactory_Impl(WellbeingRefreshWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public WellbeingRefreshWorker create(Context context, WorkerParameters parameters) {
    return delegateFactory.get(context, parameters);
  }

  public static Provider<WellbeingRefreshWorker_AssistedFactory> create(
      WellbeingRefreshWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WellbeingRefreshWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<WellbeingRefreshWorker_AssistedFactory> createFactoryProvider(
      WellbeingRefreshWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WellbeingRefreshWorker_AssistedFactory_Impl(delegateFactory));
  }
}
