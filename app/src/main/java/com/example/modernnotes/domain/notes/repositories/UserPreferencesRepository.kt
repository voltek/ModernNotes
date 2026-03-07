package com.example.modernnotes.domain.notes.repositories

import com.example.modernnotes.domain.notes.models.SortOrder
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val sortOrderFlow: Flow<SortOrder>
    suspend fun setSortOrder(sortOrder: SortOrder)

}