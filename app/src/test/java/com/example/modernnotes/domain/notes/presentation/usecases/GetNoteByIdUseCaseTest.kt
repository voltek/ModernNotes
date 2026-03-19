package com.example.modernnotes.domain.notes.presentation.usecases

import com.example.modernnotes.core.orDefault
import com.example.modernnotes.domain.notes.presentation.NoteTestFactory
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.usecases.GetNoteByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetNoteByIdUseCaseTest {

    private lateinit var mockNotesRepository: NotesRepository
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setUp() {
        mockNotesRepository = mockk()
        getNoteByIdUseCase = GetNoteByIdUseCase(mockNotesRepository)
    }

    @Test
    fun `invoke with noteId calls repository getNoteById`() = runTest {
        val testNote = NoteTestFactory.createNote()
        val testNoteId = testNote.id.orDefault()

        coEvery { mockNotesRepository.getNoteById(testNoteId) } returns testNote

        getNoteByIdUseCase(testNoteId)

        coVerify(exactly = 1) { mockNotesRepository.getNoteById(testNoteId) }
    }

}