package com.example.modernnotes.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernnotes.R
import com.example.modernnotes.core.EMPTY_STRING
import com.example.modernnotes.domain.notes.models.InvalidNoteException
import com.example.modernnotes.domain.notes.usecases.GetNoteByIdUseCase
import com.example.modernnotes.domain.notes.usecases.SaveNoteUseCase
import com.example.modernnotes.presentation.navigation.NOTE_ID_ARG
import com.example.modernnotes.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    // Type-Safe Navigation causes Method putInt in android.os.BaseBundle not mocked
    // private val route = savedStateHandle.toRoute<NoteDetailRoute>()
    //private val noteId: Int? = route.noteId

    private val noteId: Int? = savedStateHandle[NOTE_ID_ARG]

    private val isEditMode = noteId != null

    private val _uiState = MutableStateFlow(NoteDetailUiState(isEditMode = isEditMode))
    val uiState = _uiState.asStateFlow()

    private val _title = MutableStateFlow(EMPTY_STRING)
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow(EMPTY_STRING)
    val content = _content.asStateFlow()

    private val _events = Channel<NoteDetailUiEvent>()
    val events = _events.receiveAsFlow()


    init {
        if (isEditMode) getNoteById()
    }

    private fun getNoteById() {
        viewModelScope.launch {
            val id = noteId ?: return@launch

            _uiState.update { it.copy(isLoading = true) }
            try {
                val note = getNoteByIdUseCase(id)

                _title.value = note.title
                _content.value = note.content
            } catch (_: Exception) {
                _events.send(
                    NoteDetailUiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_note_unknown_exception)
                    )
                )
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                saveNoteUseCase(
                    noteId,
                    _title.value,
                    _content.value
                )
                _events.send(NoteDetailUiEvent.NavigateBack)

            } catch (e: InvalidNoteException) {
                val errorMessage = when (e.errorType) {
                    InvalidNoteException.ErrorType.EmptyTitle ->
                        UiText.StringResource(R.string.error_note_title_empty)

                    InvalidNoteException.ErrorType.EmptyContent ->
                        UiText.StringResource(R.string.error_note_content_empty)
                }

                _events.send(NoteDetailUiEvent.ShowSnackbar(errorMessage))
            } catch (_: Exception) {
                _events.send(
                    NoteDetailUiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_note_unknown_exception)
                    )
                )
            }
        }
    }
}