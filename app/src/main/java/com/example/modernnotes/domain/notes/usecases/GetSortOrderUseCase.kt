package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortOrderUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {

    operator fun invoke(): Flow<SortOrder> {
        return userPreferencesRepository.sortOrderFlow
    }
}