package com.droidtank.livewally.domain.usecase

import com.droidtank.livewally.data.model.WellbeingSnapshot
import com.droidtank.livewally.domain.repository.WellbeingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetWellbeingSnapshotUseCase @Inject constructor(
    private val repository: WellbeingRepository
) {
    suspend fun invoke(): WellbeingSnapshot = repository.getSnapshot()
    
    fun observeSnapshot(): Flow<WellbeingSnapshot> = repository.observeSnapshot()
}