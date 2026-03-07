package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note) {
        notesRepository.updateNote(note)
    }
}