package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import javax.inject.Inject

class SetSortOrderUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(sortOrder: SortOrder) {
        userPreferencesRepository.setSortOrder(sortOrder)
    }
}