package com.example.modernnotes.presentation.list

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.presentation.util.UiText

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val sortOrder: SortOrder = SortOrder.DATE_DESC
)