package com.example.modernnotes.data.notes.remote.repositories

import com.example.modernnotes.data.notes.remote.api.NotesApi
import com.example.modernnotes.data.notes.remote.mappers.toDomain
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RemoteNotesRepositoryImpl @Inject constructor(private val notesApi: NotesApi) :
    RemoteNotesRepository {

    override suspend fun fetchNotes(): Result<List<Note>> {
        return try {
            val notesDtos = notesApi.getNotes()
            val domainNotes = notesDtos.map { it.toDomain() }
            Result.success(domainNotes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}