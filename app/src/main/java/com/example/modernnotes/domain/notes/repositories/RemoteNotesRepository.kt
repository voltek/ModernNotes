package com.example.modernnotes.domain.notes.repositories

import com.example.modernnotes.domain.notes.models.Note

interface RemoteNotesRepository {

    suspend fun fetchNotes(): Result<List<Note>>
}