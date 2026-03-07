package com.example.modernnotes.domain.notes.repositories

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(sortOrder: SortOrder): Flow<List<Note>>

    fun getNoteById(noteId: Int): Flow<Note>

    suspend fun addNotes(notesList: List<Note>)

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}