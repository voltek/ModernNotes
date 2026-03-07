package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val notesRepository: NotesRepository,
                      private val preferencesRepository: UserPreferencesRepository) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Note>> {
        return preferencesRepository.sortOrderFlow.flatMapLatest { order ->
            notesRepository.getNotes(order)
        }
    }
}