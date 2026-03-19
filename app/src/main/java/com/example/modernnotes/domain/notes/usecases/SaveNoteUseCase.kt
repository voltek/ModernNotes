package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.core.orDefault
import com.example.modernnotes.domain.notes.models.InvalidNoteException
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.util.TimeProvider
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
    private val timeProvider: TimeProvider
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(noteId: Int?, title: String, content: String) {
        if (title.isBlank()) throw InvalidNoteException(InvalidNoteException.ErrorType.EmptyTitle)
        if (content.isBlank()) throw InvalidNoteException(InvalidNoteException.ErrorType.EmptyContent)

        val newNote = Note(
            noteId?.orDefault(),
            title,
            content,
            timeProvider.currentTimeMillis()
        )

        if (noteId == null) notesRepository.addNote(newNote) else notesRepository.updateNote(newNote)
    }
}