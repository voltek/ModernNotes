package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note){
        notesRepository.deleteNote(note)
    }
}