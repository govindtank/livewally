package com.example.livewally.worker;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = WellbeingRefreshWorker.class
)
public interface WellbeingRefreshWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.example.livewally.worker.WellbeingRefreshWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      WellbeingRefreshWorker_AssistedFactory factory);
}
