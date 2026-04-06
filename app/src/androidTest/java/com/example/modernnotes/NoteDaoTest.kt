package com.example.modernnotes

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.modernnotes.data.notes.local.daos.NoteDao
import com.example.modernnotes.data.notes.local.database.NotesDatabase
import com.example.modernnotes.data.notes.local.entities.NoteEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var database: NotesDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var fakeFirstNoteEntity: NoteEntity
    private lateinit var fakeSecondNoteEntity: NoteEntity
    private lateinit var fakeEntityNotes: List<NoteEntity>

    @Before
    fun setUp() {
        val roomInMemoryDatabaseBuilder = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        )
        database = roomInMemoryDatabaseBuilder
            .allowMainThreadQueries()
            .build()

        noteDao = database.noteDao()

        fakeFirstNoteEntity = NoteEntityTestFactory.createNote(
            id = 1,
            title = "A - First Note Title",
            content = "A - First Note Content",
            timeStamp = 1000L
        )
        fakeSecondNoteEntity = NoteEntityTestFactory.createNote(
            id = 2,
            title = "B - Second Note Title",
            content = "B - Second Note Content",
            timeStamp = 2000L
        )

        fakeEntityNotes = listOf(
            fakeFirstNoteEntity,
            fakeSecondNoteEntity
        )
    }

    @Test
    fun getNotesByDateAsc_returnsNotesOrderedByDateAsc() = runTest {
        noteDao.addNotes(fakeEntityNotes)

        val orderedFirstNote = noteDao.getNotesByDateAsc().first().first()

        assertEquals(fakeFirstNoteEntity.createdAt, orderedFirstNote.createdAt)
    }

    @Test
    fun getNotesByDateDesc_returnsNotesOrderedByDateDesc() = runTest {
        noteDao.addNotes(fakeEntityNotes)

        val orderedFirstNote = noteDao.getNotesByDateDesc().first().first()

        assertEquals(fakeSecondNoteEntity.createdAt, orderedFirstNote.createdAt)
    }

    @Test
    fun getNotesByTitle_returnsNotesOrderedByTitle() = runTest {
        noteDao.addNotes(fakeEntityNotes)

        val orderedFirstNote = noteDao.getNotesByTitle().first().first()

        assertEquals(fakeFirstNoteEntity.title, orderedFirstNote.title)
    }

    @Test
    fun getNoteById_givenExistingId_returnsNoteEntity() = runTest {
        noteDao.addNote(fakeFirstNoteEntity)
        val databaseNoteEntity = noteDao.getNoteById(fakeFirstNoteEntity.id)
        assertNotNull(databaseNoteEntity)
        assertEquals(fakeFirstNoteEntity.id, databaseNoteEntity?.id)
    }

    @Test
    fun getNoteById_givenNonExistingId_returnsNull() = runTest {
        noteDao.addNote(fakeFirstNoteEntity)
        val databaseNoteEntity = noteDao.getNoteById(fakeSecondNoteEntity.id)
        assertNull(databaseNoteEntity)
    }

    @Test
    fun addNotes_givenValidNotes_savesCorrectly() = runTest {
        noteDao.addNotes(fakeEntityNotes)
        val savedNotes = noteDao.getNotesByTitle().first()

        assertTrue(savedNotes.size == fakeEntityNotes.size)
    }

    @Test
    fun addNotes_givenDuplicateIds_replacesExisting() = runTest {
        val newNotes = listOf(
            fakeFirstNoteEntity.copy(
                title = "New First Note Title",
                content = "New First Content Title"
            ),
            fakeSecondNoteEntity.copy(
                title = "New Second Note Title",
                content = "New Second Content Title"
            )
        )
        noteDao.addNotes(fakeEntityNotes)
        noteDao.addNotes(newNotes)

        val savedNotes = noteDao.getNotesByTitle().first()

        assertTrue(savedNotes.size == newNotes.size)
        assertEquals(newNotes.first().title, savedNotes.first().title)
        assertEquals(newNotes[1].title, savedNotes[1].title)
    }

    @Test
    fun addNote_givenValidNote_savesCorrectly() = runTest {
        noteDao.addNote(fakeFirstNoteEntity)

        val savedNote = noteDao.getNoteById(fakeFirstNoteEntity.id)
        assertNotNull(savedNote)
        assertEquals(fakeFirstNoteEntity.title, savedNote?.title)
        assertEquals(fakeFirstNoteEntity.content, savedNote?.content)
    }

    @Test
    fun addNote_givenDuplicateId_replacesExisting() = runTest {
        val newNote =
            fakeFirstNoteEntity.copy(title = "New Note Title", content = "New Content Title")

        noteDao.addNote(fakeFirstNoteEntity)
        noteDao.addNote(newNote)

        val savedNotes = noteDao.getNotesByTitle().first()
        val originalNote = savedNotes.firstOrNull { it.title == fakeFirstNoteEntity.title }
        val replacedNote = noteDao.getNoteById(newNote.id)

        assertNotNull(replacedNote)
        assertEquals(newNote.title, replacedNote?.title)
        assertEquals(newNote.content, replacedNote?.content)
        assertNull(originalNote)
    }

    @Test
    fun updateNote_givenNoteEntity_updatesExisting() = runTest {
        val updatedNote =
            fakeFirstNoteEntity.copy(title = "Updated Note Title", content = "Updated Note Content")

        noteDao.addNote(fakeFirstNoteEntity)
        noteDao.updateNote(updatedNote)

        val databaseNote = noteDao.getNoteById(updatedNote.id)

        assertEquals(updatedNote.title, databaseNote?.title)
        assertEquals(updatedNote.content, databaseNote?.content)
    }

    @Test
    fun deleteNote_givenNoteEntity_deletesExisting() = runTest {
        noteDao.addNote(fakeFirstNoteEntity)
        noteDao.deleteNote(fakeFirstNoteEntity)

        val databaseNotes = noteDao.getNoteById(fakeFirstNoteEntity.id)

        assertNull(databaseNotes)
    }

    @After
    fun tearDown() {
        database.close()
    }
}