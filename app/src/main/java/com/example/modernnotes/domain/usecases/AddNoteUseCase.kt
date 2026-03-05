package com.example.modernnotes.domain.usecases

import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.repositories.NotesRepository

class AddNoteUseCase(private val notesRepository: NotesRepository) {

    suspend operator fun invoke(note: Note){
        notesRepository.addNote(note)
    }
}