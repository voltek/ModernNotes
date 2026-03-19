package com.example.modernnotes.domain.notes.presentation.usecases

import com.example.modernnotes.domain.notes.models.InvalidNoteException
import com.example.modernnotes.domain.notes.presentation.NoteTestFactory
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.usecases.SaveNoteUseCase
import com.example.modernnotes.domain.notes.presentation.util.FakeTimeProvider
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SaveNoteUseCaseTest {

    private lateinit var notesRepository: NotesRepository
    private lateinit var saveNoteUseCase: SaveNoteUseCase
    private val fakeTimeProvider = FakeTimeProvider(NoteTestFactory.VALID_TIME_STAMP)

    @Before
    fun setup() {
        notesRepository = mockk(relaxed = true)
        saveNoteUseCase = SaveNoteUseCase(notesRepository, fakeTimeProvider)
    }

    @Test
    fun `given null noteId when saving then calls addNote`() = runTest {
        val expectedNote = NoteTestFactory.createNote(id = null)

        saveNoteUseCase(
            null,
            expectedNote.title,
            expectedNote.content
        )

        coVerify(exactly = 1) { notesRepository.addNote(expectedNote) }
    }

    @Test
    fun `invoke with not null noteId calls updateNote`() = runTest {
        val expectedNote = NoteTestFactory.createNote()

        saveNoteUseCase(
            expectedNote.id, expectedNote.title, expectedNote.content
        )

        coVerify(exactly = 1) { notesRepository.updateNote(expectedNote) }
    }

    @Test
    fun `given empty title when saving note then throws EmptyTitle exception`() = runTest {
        val exception = assertFailsWith<InvalidNoteException> {
            saveNoteUseCase(
                NoteTestFactory.NOTE_ID,
                NoteTestFactory.EMPTY,
                NoteTestFactory.VALID_CONTENT
            )
        }
        assertEquals(InvalidNoteException.ErrorType.EmptyTitle, exception.errorType)
    }

    @Test
    fun `invoke with EmptyContent throws InvalidNoteException with EmptyContent`() = runTest {
        val exception = assertFailsWith<InvalidNoteException> {
            saveNoteUseCase(
                NoteTestFactory.NOTE_ID,
                NoteTestFactory.VALID_TITLE,
                NoteTestFactory.EMPTY
            )
        }

        assertEquals(InvalidNoteException.ErrorType.EmptyContent, exception.errorType)
    }
}