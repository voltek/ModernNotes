package com.example.modernnotes.data.notes.local.repositories

import com.example.modernnotes.data.notes.local.daos.NoteDao
import com.example.modernnotes.data.notes.local.mappers.toDomain
import com.example.modernnotes.data.notes.local.mappers.toEntity
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
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

    override suspend fun getNoteById(noteId: Int): Note {
        return noteDao.getNoteById(noteId).toDomain()
    }

    override suspend fun addNotes(notesList: List<Note>) {
        noteDao.addNotes(notesList.toEntity())
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