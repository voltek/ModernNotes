package com.example.modernnotes.data.notes.local.mappers

import com.example.modernnotes.data.notes.local.NoteEntityTestFactory
import com.example.modernnotes.data.notes.local.entities.NoteEntity
import com.example.modernnotes.domain.notes.models.Note
import com.example.modernnotes.domain.notes.NoteTestFactory
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteEntityMapperTest {

    private lateinit var fakeNoteEntity: NoteEntity
    private lateinit var fakeNote: Note

    @Before
    fun setUp() {
        fakeNoteEntity = NoteEntityTestFactory.createNoteEntity()
        fakeNote =
            NoteTestFactory.createNote(
                fakeNoteEntity.id,
                fakeNoteEntity.title,
                fakeNoteEntity.content,
                fakeNoteEntity.createdAt
            )
    }

    @Test
    fun `given noteEntity when toDomain then returns Note`() {
        val note = fakeNoteEntity.toDomain()

        assertEquals(fakeNoteEntity.id, note.id)
        assertEquals(fakeNoteEntity.title, note.title)
        assertEquals(fakeNoteEntity.content, note.content)
        assertEquals(fakeNoteEntity.createdAt, note.createdAt)
    }

    @Test
    fun `given Note when toEntity then returns NoteEntity`() {
        val fakeNoteEntity = fakeNote.toEntity()

        assertEquals(fakeNote.id, fakeNoteEntity.id)
        assertEquals(fakeNote.title, fakeNoteEntity.title)
        assertEquals(fakeNote.content, fakeNoteEntity.content)
        assertEquals(fakeNote.createdAt, fakeNoteEntity.createdAt)
    }
}