package com.example.modernnotes.domain.repositories

import com.example.modernnotes.domain.models.SortOrder
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val sortOrderFlow: Flow<SortOrder>
    suspend fun setSortOrder(sortOrder: SortOrder)

}