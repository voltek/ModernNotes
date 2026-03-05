package com.example.modernnotes.domain.repositories

import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.models.SortOrder
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(sortOrder: SortOrder): Flow<List<Note>>

    fun getNoteById(noteId: Int): Flow<Note>

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}