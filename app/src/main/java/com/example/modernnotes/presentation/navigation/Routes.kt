package com.example.modernnotes.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object NotesListRoute

@Serializable
data class NoteDetailRoute(val noteId: Int? = null)