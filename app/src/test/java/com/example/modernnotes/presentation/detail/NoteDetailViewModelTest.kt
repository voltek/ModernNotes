package com.example.modernnotes.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.modernnotes.R
import com.example.modernnotes.core.orDefault
import com.example.modernnotes.domain.notes.models.InvalidNoteException
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.NoteTestFactory
import com.example.modernnotes.domain.notes.usecases.GetNoteByIdUseCase
import com.example.modernnotes.domain.notes.usecases.SaveNoteUseCase
import com.example.modernnotes.presentation.navigation.NOTE_ID_ARG
import com.example.modernnotes.presentation.util.UiText
import com.example.modernnotes.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NoteDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var saveNoteUseCase: SaveNoteUseCase
    private lateinit var fakeNote: Note

    @Before
    fun setUp() {
        getNoteByIdUseCase = mockk()
        saveNoteUseCase = mockk()
        fakeNote = NoteTestFactory.createNote()
    }

    @Test
    fun `given noteId null when init viewModel then isEditMode false`() = runTest {
        val viewModel = createViewModel(null)

        assertFalse(viewModel.uiState.value.isEditMode)
        coVerify(exactly = 0) { getNoteByIdUseCase(any()) }
    }

    @Test
    fun `given noteId not null when init viewModel then isEditMode true`() = runTest {
        val noteId = fakeNote.id.orDefault()
        coEvery { getNoteByIdUseCase(noteId) } returns fakeNote

        val viewModel = createViewModel(noteId)
        val uiState = viewModel.uiState.value

        assertTrue(uiState.isEditMode)

        assertEquals(fakeNote.title, viewModel.title.value)
        assertEquals(fakeNote.content, viewModel.content.value)

        coVerify(exactly = 1) { getNoteByIdUseCase(noteId) }
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `given noteId not null when getNoteById then throws Exception and shows SnackBar`() =
        runTest {
            val noteId = fakeNote.id.orDefault()
            coEvery { getNoteByIdUseCase(noteId) } throws Exception(GENERIC_EXCEPTION_MESSAGE)

            createViewModel(noteId).events.test {
                advanceUntilIdle()
                val event = awaitItem()

                assertTrue(event is NoteDetailUiEvent.ShowSnackbar)
                assertEquals(
                    R.string.error_note_unknown_exception,
                    (event.message as UiText.StringResource).resId
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `updateTitle updates title StateFlow`() {
        val viewModel = createViewModel(null)
        viewModel.updateTitle(fakeNote.title)
        assertEquals(fakeNote.title, viewModel.title.value)
    }

    @Test
    fun `updateContent updates content StateFlow`() {
        val viewModel = createViewModel(null)
        viewModel.updateContent(fakeNote.content)
        assertEquals(fakeNote.content, viewModel.content.value)
    }

    @Test
    fun `given valid note when saveNote then emits NavigateBack`() = runTest {
        coEvery { saveNoteUseCase(null, fakeNote.title, fakeNote.content) } just Runs
        val viewModel = createViewModel(null)

        viewModel.updateTitle(fakeNote.title)
        viewModel.updateContent(fakeNote.content)

        viewModel.events.test {
            viewModel.saveNote()
            advanceUntilIdle()

            val event = awaitItem()

            assertTrue(event is NoteDetailUiEvent.NavigateBack)
            coVerify(exactly = 1) { saveNoteUseCase(null, fakeNote.title, fakeNote.content) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given note with empty title when saveNote then emits EmptyTitle`() = runTest {
        val viewModel = createViewModel(null)

        coEvery {
            saveNoteUseCase(
                any(),
                NoteTestFactory.EMPTY,
                any()
            )
        } throws (InvalidNoteException(
            InvalidNoteException.ErrorType.EmptyTitle
        ))

        viewModel.updateTitle(NoteTestFactory.EMPTY)
        viewModel.updateContent(fakeNote.content)

        viewModel.events.test {
            viewModel.saveNote()
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is NoteDetailUiEvent.ShowSnackbar)
            assertEquals(
                R.string.error_note_title_empty,
                (event.message as UiText.StringResource).resId
            )
            coVerify(exactly = 1) { saveNoteUseCase(null, NoteTestFactory.EMPTY, fakeNote.content) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given note with empty content when saveNote then emits EmptyContent`() = runTest {
        val viewModel = createViewModel(null)
        coEvery {
            saveNoteUseCase(
                null,
                fakeNote.title,
                NoteTestFactory.EMPTY
            )
        } throws InvalidNoteException(InvalidNoteException.ErrorType.EmptyContent)

        viewModel.updateTitle(fakeNote.title)

        viewModel.events.test {
            viewModel.saveNote()
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is NoteDetailUiEvent.ShowSnackbar)
            assertEquals(
                R.string.error_note_content_empty,
                (event.message as UiText.StringResource).resId
            )
            coVerify { saveNoteUseCase(null, fakeNote.title, NoteTestFactory.EMPTY) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given note with title and content when saveNote then emits Exception`() = runTest {
        coEvery { saveNoteUseCase(null, fakeNote.title, fakeNote.content) } throws Exception(
            GENERIC_EXCEPTION_MESSAGE
        )

        val viewModel = createViewModel(null)

        viewModel.updateTitle(fakeNote.title)
        viewModel.updateContent(fakeNote.content)

        viewModel.events.test {
            viewModel.saveNote()
            advanceUntilIdle()

            val event = awaitItem()

            assertTrue(event is NoteDetailUiEvent.ShowSnackbar)
            assertEquals(
                R.string.error_note_unknown_exception,
                (event.message as UiText.StringResource).resId
            )
        }
    }

    private fun createViewModel(noteId: Int?): NoteDetailViewModel {
        savedStateHandle = SavedStateHandle(mapOf(NOTE_ID_ARG to noteId))
        return NoteDetailViewModel(savedStateHandle, getNoteByIdUseCase, saveNoteUseCase)
    }

    companion object {
        private const val GENERIC_EXCEPTION_MESSAGE = "Database crash"
    }
}