package com.example.modernnotes.domain.notes.usecases

import com.example.modernnotes.domain.notes.NoteTestFactory
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var mockNotesRepository: NotesRepository

    @Before
    fun setUp() {
        mockNotesRepository = mockk()
        deleteNoteUseCase = DeleteNoteUseCase(mockNotesRepository)
    }

    @Test
    fun `given valid note when delete then repository deletes note`() = runTest {
        coEvery { mockNotesRepository.deleteNote(any()) } just Runs
        val testNote = NoteTestFactory.createNote()

        deleteNoteUseCase(testNote)

        coVerify(exactly = 1) { mockNotesRepository.deleteNote(testNote) }
    }
}