package com.example.modernnotes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernnotes.R
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.usecases.GetNotesUseCase
import com.example.modernnotes.domain.notes.usecases.GetSortOrderUseCase
import com.example.modernnotes.domain.notes.usecases.SetSortOrderUseCase
import com.example.modernnotes.domain.notes.usecases.SyncNotesUseCase
import com.example.modernnotes.presentation.util.PresentationConstants.SHARING_STARTED_STOP_TIMEOUT_MILLIS
import com.example.modernnotes.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    getNotesUseCase: GetNotesUseCase,
    private val syncNotesUseCase: SyncNotesUseCase,
    private val setSortOrderUseCase: SetSortOrderUseCase,
    getSortOrderUseCase: GetSortOrderUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _error = MutableStateFlow<UiText?>(null)

    val uiState = combine(
        getNotesUseCase(),
        _isLoading,
        _error,
        getSortOrderUseCase(),
        ::buildUiState
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SHARING_STARTED_STOP_TIMEOUT_MILLIS),
        NotesListUiState()
    )

    init {
        syncNotes()
    }

    private fun buildUiState(
        notes: List<Note>,
        isLoading: Boolean,
        error: UiText?,
        sortOrder: SortOrder
    ): NotesListUiState {
        return NotesListUiState(
            notes = notes,
            isLoading = isLoading && notes.isEmpty(),
            error = error,
            sortOrder = sortOrder
        )
    }

    private fun syncNotes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            syncNotesUseCase().onFailure {
                _error.value = UiText.StringResource(R.string.network_error, it.localizedMessage)
            }

            _isLoading.value = false
        }
    }

    fun changeSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            setSortOrderUseCase(sortOrder)
        }
    }
}