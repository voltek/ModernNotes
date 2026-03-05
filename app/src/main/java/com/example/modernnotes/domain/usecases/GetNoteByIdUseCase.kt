package com.example.modernnotes.domain.usecases

import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetNoteByIdUseCase(private val notesRepository: NotesRepository) {

    operator fun invoke(noteId: Int): Flow<Note> {
        return notesRepository.getNoteById(noteId)
    }
}