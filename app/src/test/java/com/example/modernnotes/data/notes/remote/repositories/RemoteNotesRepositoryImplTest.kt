package com.example.modernnotes.data.notes.remote.repositories

import com.example.modernnotes.data.notes.remote.api.NotesApi
import com.example.modernnotes.util.MockWebServerRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteNotesRepositoryImplTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()
    private lateinit var notesApi: NotesApi
    private lateinit var repository: RemoteNotesRepositoryImpl

    @Before
    fun setUp() {
        notesApi = mockWebServerRule.createRetrofit().create(NotesApi::class.java)
        repository = RemoteNotesRepositoryImpl(notesApi)
    }

    @Test
    fun `fetchNotes returns success result when API returns 200 OK`() = runTest {
        val fakeJsonResponse = """
            [
                {
                    "note_id": 100,
                    "note_title": "Nota desde la nube",
                    "note_content": "Contenido mockeado",
                    "note_created_at": 1700000000
                }
            ]
        """.trimIndent()

        mockWebServerRule.enqueueResponse(fakeJsonResponse)

        val result = repository.fetchNotes()

        assertTrue(result.isSuccess)
        val notes = result.getOrNull()

        assertEquals(1, notes?.size)
        assertEquals("Nota desde la nube", notes?.get(0)?.title)
    }

    @Test
    fun `fetchNotes returns failure when API returns 500 error`() = runTest {
        mockWebServerRule.enqueueResponse("", 500)
        val result = repository.fetchNotes()
        assertTrue(result.isFailure)
    }
}
