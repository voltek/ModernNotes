package com.example.modernnotes.presentation.detail

import com.example.modernnotes.presentation.util.UiText

sealed class NoteDetailUiEvent {
    data class ShowSnackbar(val message: UiText) : NoteDetailUiEvent()
    object NavigateBack : NoteDetailUiEvent()
}