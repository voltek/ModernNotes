package com.example.modernnotes.domain.notes.presentation.usecases

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.presentation.NoteTestFactory
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import com.example.modernnotes.domain.notes.usecases.SyncNotesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SyncNotesUseCaseTest {

    private lateinit var remoteNotesRepository: RemoteNotesRepository
    private lateinit var notesRepository: NotesRepository
    private lateinit var syncNotesUseCase: SyncNotesUseCase
    private lateinit var fakeNotes: List<Note>

    @Before
    fun setUp() {
        remoteNotesRepository = mockk()
        notesRepository = mockk(relaxed = true)
        syncNotesUseCase = SyncNotesUseCase(remoteNotesRepository, notesRepository)
        fakeNotes = listOf(NoteTestFactory.createNote())
    }


    @Test
    fun `given fetch success when invoked then saves notes and returns success`() = runTest {
        val successResultWithNotes = Result.success(fakeNotes)
        coEvery { remoteNotesRepository.fetchNotes() } returns successResultWithNotes

        val result = syncNotesUseCase()

        coVerify(exactly = 1) { remoteNotesRepository.fetchNotes() }
        coVerify(exactly = 1) { notesRepository.addNotes(any()) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `given fetch failure when invoked then returns failure`() = runTest {
        val exception = Exception("Network Exception")
        val failureResult = Result.failure<List<Note>>(exception)
        coEvery { remoteNotesRepository.fetchNotes() } returns failureResult

        val result = syncNotesUseCase()

        coVerify(exactly = 1) { remoteNotesRepository.fetchNotes() }
        coVerify(exactly = 0) { notesRepository.addNotes(any()) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}