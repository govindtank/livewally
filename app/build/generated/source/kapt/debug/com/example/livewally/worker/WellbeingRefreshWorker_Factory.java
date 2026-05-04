package com.example.livewally.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.livewally.domain.repository.WellbeingRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class WellbeingRefreshWorker_Factory {
  private final Provider<WellbeingRepository> repositoryProvider;

  public WellbeingRefreshWorker_Factory(Provider<WellbeingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public WellbeingRefreshWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, repositoryProvider.get());
  }

  public static WellbeingRefreshWorker_Factory create(
      Provider<WellbeingRepository> repositoryProvider) {
    return new WellbeingRefreshWorker_Factory(repositoryProvider);
  }

  public static WellbeingRefreshWorker newInstance(Context appContext,
      WorkerParameters workerParams, WellbeingRepository repository) {
    return new WellbeingRefreshWorker(appContext, workerParams, repository);
  }
}
