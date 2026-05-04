package com.example.livewally.domain.usecase;

import com.example.livewally.data.model.WellbeingSnapshot;
import com.example.livewally.domain.repository.WellbeingRepository;
import javax.inject.Inject;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/livewally/domain/usecase/GetWellbeingSnapshotUseCase;", "", "repository", "Lcom/example/livewally/domain/repository/WellbeingRepository;", "(Lcom/example/livewally/domain/repository/WellbeingRepository;)V", "invoke", "Lcom/example/livewally/data/model/WellbeingSnapshot;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeSnapshot", "Lkotlinx/coroutines/flow/Flow;", "app_debug"})
public final class GetWellbeingSnapshotUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.example.livewally.domain.repository.WellbeingRepository repository = null;
    
    @javax.inject.Inject()
    public GetWellbeingSnapshotUseCase(@org.jetbrains.annotations.NotNull()
    com.example.livewally.domain.repository.WellbeingRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.livewally.data.model.WellbeingSnapshot> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.example.livewally.data.model.WellbeingSnapshot> observeSnapshot() {
        return null;
    }
}