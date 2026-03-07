package com.example.modernnotes.data.notes.remote.api

import com.example.modernnotes.data.notes.remote.dtos.NoteDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {

    @GET(NOTES)
    suspend fun getNotes(): List<NoteDto>

    @GET(NOTES_ID)
    suspend fun getNoteById(@Path(ID) noteId: Int): NoteDto

    @POST("$NOTES/add_note")
    suspend fun addNote(noteDto: NoteDto)

    @PUT(NOTES_ID)
    suspend fun updateNote(@Path(ID) noteId: Int, noteDto: NoteDto)

    @DELETE(NOTES_ID)
    suspend fun deleteNote(@Path(ID) noteId: Int)


    companion object {
        private const val NOTES = "notes"
        private const val ID = "id"
        private const val NOTES_ID = "$NOTES/{$ID}"
    }
}