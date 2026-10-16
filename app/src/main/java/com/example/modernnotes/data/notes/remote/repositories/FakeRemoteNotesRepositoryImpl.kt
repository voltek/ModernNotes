package com.example.modernnotes.data.notes.remote.repositories

import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.repositories.RemoteNotesRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

class FakeRemoteNotesRepositoryImpl @Inject constructor() : RemoteNotesRepository {

    /**
     * No backend exists for this learning project.
     * Bound in RepositoryModule so the app shows data and the loading state;
     * the real implementation is RemoteNotesRepositoryImpl, covered by MockWebServer tests
     */
    override suspend fun fetchNotes(): Result<List<Note>> {
        return try {
            delay(2000.milliseconds)

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
            Result.success(dummyNotes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}