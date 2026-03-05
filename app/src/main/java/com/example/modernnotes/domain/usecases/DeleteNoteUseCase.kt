package com.example.modernnotes.domain.usecases

import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.repositories.NotesRepository

class DeleteNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note){
        notesRepository.deleteNote(note)
    }
}