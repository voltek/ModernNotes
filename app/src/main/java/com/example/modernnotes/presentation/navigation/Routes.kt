package com.example.modernnotes.presentation.navigation

import kotlinx.serialization.Serializable

const val NOTE_ID_ARG = "noteId"

@Serializable
object NotesListRoute

@Serializable
data class NoteDetailRoute(val noteId: Int? = null)