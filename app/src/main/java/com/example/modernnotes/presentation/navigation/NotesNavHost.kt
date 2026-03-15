package com.example.modernnotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modernnotes.presentation.detail.NoteDetailScreen
import com.example.modernnotes.presentation.list.NotesListScreen

@Composable
fun NotesNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NotesListRoute) {
        composable<NotesListRoute> {
            NotesListScreen(
                onNoteClick = { id -> navController.navigate(NoteDetailRoute(noteId = id)) },
                onAddNote = { navController.navigate(NoteDetailRoute()) })
        }

        composable<NoteDetailRoute> {
            NoteDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}