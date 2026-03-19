package com.example.modernnotes.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.modernnotes.R
import com.example.modernnotes.core.orDefault
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.presentation.util.toDisplayString

@Composable
fun NotesListScreen(
    onNoteClick: (id: Int) -> Unit,
    onAddNote: () -> Unit,
    viewModel: NotesListViewModel = hiltViewModel()
) {
    val notesListUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NotesListTopBar(
                notesListUiState.sortOrder,
                { sortOrder -> viewModel.changeSortOrder(sortOrder) }
            )
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onAddNote) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.cd_notes_list_add_new_note_fab)
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notesListUiState.notes) { note ->
                    Text(
                        text = "${note.title} - ${note.content}",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable(true) {
                                onNoteClick(note.id.orDefault())
                            }
                    )
                }
            }

            if (notesListUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            notesListUiState.error?.run {
                Text(
                    text = this.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListTopBar(
    selectedSortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.notes_list_screen_my_notes_title)) },
        actions = {
            SortOrderMenu(
                selectedSortOrder = selectedSortOrder,
                onSortOrderSelected = onSortOrderSelected
            )
        }
    )
}

@Composable
fun SortOrderMenu(
    selectedSortOrder: SortOrder,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { isDropDownExpanded = true }
    ) {
        Text(selectedSortOrder.toDisplayString())
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = stringResource(R.string.cd_notes_list_screen_sort_order_button)
        )
    }

    DropdownMenu(
        expanded = isDropDownExpanded,
        onDismissRequest = { isDropDownExpanded = false }
    ) {
        SortOrder.entries.forEach { sortOrder ->
            DropdownMenuItem(
                text = { Text(sortOrder.toDisplayString()) },
                onClick = {
                    onSortOrderSelected(sortOrder)
                    isDropDownExpanded = false
                },
                trailingIcon = {
                    if (sortOrder == selectedSortOrder) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
        }
    }
}