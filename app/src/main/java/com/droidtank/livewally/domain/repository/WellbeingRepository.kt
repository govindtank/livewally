package com.droidtank.livewally.domain.repository

import com.droidtank.livewally.data.model.WellbeingSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WellbeingRepository {
    val snapshot: StateFlow<WellbeingSnapshot>
    val isLoading: StateFlow<Boolean>
    val error: StateFlow<String?>
    
    fun observeSnapshot(): Flow<WellbeingSnapshot>
    suspend fun getSnapshot(): WellbeingSnapshot
    suspend fun refresh()
}