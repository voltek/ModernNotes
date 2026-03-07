package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val notesRepository: NotesRepository) {

    operator fun invoke(noteId: Int): Flow<Note> {
        return notesRepository.getNoteById(noteId)
    }
}