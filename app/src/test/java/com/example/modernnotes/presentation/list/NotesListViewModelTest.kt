package com.example.modernnotes.presentation.list

import app.cash.turbine.test
import com.example.modernnotes.R
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.NoteTestFactory
import com.example.modernnotes.domain.notes.usecases.GetNotesUseCase
import com.example.modernnotes.domain.notes.usecases.GetSortOrderUseCase
import com.example.modernnotes.domain.notes.usecases.SetSortOrderUseCase
import com.example.modernnotes.domain.notes.usecases.SyncNotesUseCase
import com.example.modernnotes.presentation.util.UiText
import com.example.modernnotes.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NotesListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var getNotesUseCase: GetNotesUseCase
    lateinit var syncNotesUseCase: SyncNotesUseCase
    lateinit var setSortOrderUseCase: SetSortOrderUseCase
    lateinit var getSortOrderUseCase: GetSortOrderUseCase
    lateinit var fakeNotesList: List<Note>

    @Before
    fun setUp() {
        getNotesUseCase = mockk()
        syncNotesUseCase = mockk()
        setSortOrderUseCase = mockk()
        getSortOrderUseCase = mockk()
        fakeNotesList = listOf(NoteTestFactory.createNote())

        every { getNotesUseCase() } returns flowOf(emptyList())
        every { getSortOrderUseCase() } returns flowOf(SortOrder.DATE_DESC)
        coEvery { syncNotesUseCase() } returns Result.success(emptyList())
    }

    @Test
    fun `given empty notes and sync in progress when init then isLoading is true`() = runTest {
        coEvery { syncNotesUseCase() } coAnswers {
            delay(1000)
            Result.success(emptyList())
        }

        createViewModel().uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)
            assertTrue(initialState.notes.isEmpty())
        }
    }

    @Test
    fun `given empty notes when sync completes then isLoading is false`() = runTest {
        coEvery { syncNotesUseCase() } coAnswers {
            delay(1000)
            Result.success(emptyList())
        }

        createViewModel().uiState.test {
            skipItems(1)
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertTrue(loadedState.notes.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given not empty notes and sync in progress when init then isLoading is false`() = runTest {
        every { getNotesUseCase() } returns flowOf(fakeNotesList)
        coEvery { syncNotesUseCase() } coAnswers {
            delay(1000)
            Result.success(emptyList())
        }

        createViewModel().uiState.test {
            val initialState = awaitItem()

            assertFalse(initialState.isLoading)
            assertEquals(fakeNotesList, initialState.notes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given syncNotes when init then error is null`() = runTest {
        coEvery { syncNotesUseCase() } coAnswers {
            delay(1000)
            Result.success(emptyList())
        }

        createViewModel().uiState.test {
            skipItems(1)

            val loadedState = awaitItem()
            assertNull(loadedState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given syncNotes when init then error is UiText`() = runTest {
        coEvery { syncNotesUseCase() } coAnswers {
            delay(1000)
            Result.failure(Exception("Generic Error"))
        }

        createViewModel().uiState.test {
            skipItems(1)
            val loadedState = awaitItem()

            assertNotNull(loadedState.error)
            assertEquals(R.string.network_error, (loadedState.error as UiText.StringResource).resId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given new SortOrder when changeSortOrder then calls setOrderUseCase`() = runTest {
        coEvery { setSortOrderUseCase(SortOrder.TITLE) } just Runs

        val viewModel = createViewModel()
        viewModel.changeSortOrder(SortOrder.TITLE)
        advanceUntilIdle()

        coVerify(exactly = 1) { setSortOrderUseCase(SortOrder.TITLE) }
    }

    private fun createViewModel() = NotesListViewModel(
        getNotesUseCase,
        syncNotesUseCase,
        setSortOrderUseCase,
        getSortOrderUseCase
    )
}