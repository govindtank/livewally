package com.example.livewally.domain.usecase;

import com.example.livewally.domain.repository.WellbeingRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class GetWellbeingSnapshotUseCase_Factory implements Factory<GetWellbeingSnapshotUseCase> {
  private final Provider<WellbeingRepository> repositoryProvider;

  public GetWellbeingSnapshotUseCase_Factory(Provider<WellbeingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetWellbeingSnapshotUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetWellbeingSnapshotUseCase_Factory create(
      Provider<WellbeingRepository> repositoryProvider) {
    return new GetWellbeingSnapshotUseCase_Factory(repositoryProvider);
  }

  public static GetWellbeingSnapshotUseCase newInstance(WellbeingRepository repository) {
    return new GetWellbeingSnapshotUseCase(repository);
  }
}
