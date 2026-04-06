package com.example.modernnotes.domain.notes.usecases

import app.cash.turbine.test
import com.example.modernnotes.domain.notes.NoteTestFactory
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetNotesUseCaseTest {

    private lateinit var mockNotesRepository: NotesRepository
    private lateinit var mockUserPreferencesRepository: UserPreferencesRepository
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeNotes: List<Note>

    @Before
    fun setUp() {
        mockNotesRepository = mockk()
        mockUserPreferencesRepository = mockk()

        getNotesUseCase = GetNotesUseCase(mockNotesRepository, mockUserPreferencesRepository)
        fakeNotes = listOf(NoteTestFactory.createNote())
    }

    @Test
    fun `invoke emits notes sorted by title`() = runTest {
        every { mockUserPreferencesRepository.sortOrderFlow } returns flowOf(SortOrder.TITLE)
        every { mockNotesRepository.getNotes(SortOrder.TITLE) } returns flowOf(fakeNotes)

        getNotesUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeNotes, emission)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits notes sorted by DATE_DESC`() = runTest {
        every { mockUserPreferencesRepository.sortOrderFlow } returns flowOf(SortOrder.DATE_DESC)
        every { mockNotesRepository.getNotes(SortOrder.DATE_DESC) } returns flowOf(fakeNotes)

        getNotesUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeNotes, emission)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits notes sorted by DATE_ASC`() = runTest {
        every { mockUserPreferencesRepository.sortOrderFlow } returns flowOf(SortOrder.DATE_ASC)
        every { mockNotesRepository.getNotes(SortOrder.DATE_ASC) } returns flowOf(fakeNotes)

        getNotesUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeNotes, emission)
            awaitComplete()
        }
    }
}