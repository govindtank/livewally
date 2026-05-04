package com.example.livewally.domain.repository;

import com.example.livewally.data.model.WellbeingSnapshot;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u000b\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eH&J\u000e\u0010\u000f\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010\fR\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/livewally/domain/repository/WellbeingRepository;", "", "error", "Lkotlinx/coroutines/flow/StateFlow;", "", "getError", "()Lkotlinx/coroutines/flow/StateFlow;", "isLoading", "", "snapshot", "Lcom/example/livewally/data/model/WellbeingSnapshot;", "getSnapshot", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeSnapshot", "Lkotlinx/coroutines/flow/Flow;", "refresh", "", "app_debug"})
public abstract interface WellbeingRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<com.example.livewally.data.model.WellbeingSnapshot> getSnapshot();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<java.lang.String> getError();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.livewally.data.model.WellbeingSnapshot> observeSnapshot();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSnapshot(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.livewally.data.model.WellbeingSnapshot> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object refresh(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}