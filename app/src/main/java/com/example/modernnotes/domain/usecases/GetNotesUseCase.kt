package com.example.modernnotes.domain.usecases

import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.repositories.NotesRepository
import com.example.modernnotes.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetNotesUseCase(private val notesRepository: NotesRepository,
                      private val preferencesRepository: UserPreferencesRepository) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Note>> {
        return preferencesRepository.sortOrderFlow.flatMapLatest { order ->
            notesRepository.getNotes(order)
        }
    }

}