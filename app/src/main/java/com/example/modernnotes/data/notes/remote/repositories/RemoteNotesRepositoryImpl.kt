package com.example.modernnotes.data.notes.remote.repositories

import com.example.modernnotes.data.notes.remote.api.NotesApi
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class RemoteNotesRepositoryImpl @Inject constructor(private val notesApi: NotesApi) :
    RemoteNotesRepository {

    override suspend fun fetchNotes(): Result<List<Note>> {
        return try {

                //TODO Remove this: This is just for visual tests:
                delay(2000)

                val dummyNotes = listOf(
                    Note(
                        id = 1,
                        title = "Learn Clean Architecture",
                        content = "Step 1: Understand Flow",
                        createdAt = 1680000000L
                    ),
                    Note(
                        id = 2,
                        title = "Cook the food",
                        content = "Buy some chicken",
                        createdAt = 1680000010L
                    )
                )

/*
            // TODO This is the real code to be enabled after deleted the previous dummy data
            // and has to be enabled when you execute Unit Testing
            val notesDtos = notesApi.getNotes()
            val domainNotes = notesDtos.map { it.toDomain() }
            Result.success(domainNotes)
 */

            Result.success(dummyNotes)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}