package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import javax.inject.Inject

class SyncNotesUseCase @Inject constructor(
    private val remoteNotesRepository: RemoteNotesRepository,
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(): Result<List<Note>> {
        val fetchNotesResult = remoteNotesRepository.fetchNotes()
        fetchNotesResult.onSuccess { noteModels -> notesRepository.addNotes(noteModels) }

        return fetchNotesResult
    }
}