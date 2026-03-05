package com.example.modernnotes.data.note.repositories

import com.example.modernnotes.data.note.local.daos.NoteDao
import com.example.modernnotes.data.note.mappers.toDomain
import com.example.modernnotes.data.note.mappers.toEntity
import com.example.modernnotes.domain.models.Note
import com.example.modernnotes.domain.models.SortOrder
import com.example.modernnotes.domain.repositories.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val noteDao: NoteDao
) : NotesRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNotes(sortOrder: SortOrder): Flow<List<Note>> {
        return when (sortOrder) {
            SortOrder.DATE_DESC -> noteDao.getNotesByDateDesc()
            SortOrder.DATE_ASC -> noteDao.getNotesByDateAsc()
            SortOrder.TITLE -> noteDao.getNotesByTitle()
        }.map { it.toDomain() }
    }

    override fun getNoteById(noteId: Int): Flow<Note> {
        return noteDao.getNoteById(noteId).map { it.toDomain() }
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }
}