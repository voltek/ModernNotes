package com.example.modernnotes.data.notes.local.repositories

import app.cash.turbine.test
import com.example.modernnotes.data.notes.local.NoteEntityTestFactory
import com.example.modernnotes.data.notes.local.daos.NoteDao
import com.example.modernnotes.data.notes.local.entities.NoteEntity
import com.example.modernnotes.domain.notes.models.SortOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


@Suppress("UnusedFlow")
class NotesRepositoryImplTest {

    private lateinit var noteDao: NoteDao
    private lateinit var notesRepositoryImpl: NotesRepositoryImpl
    private lateinit var fakeEntityNotes: List<NoteEntity>

    @Before
    fun setUp() {
        noteDao = mockk(relaxed = true)
        notesRepositoryImpl = NotesRepositoryImpl(noteDao)
        fakeEntityNotes = listOf(NoteEntityTestFactory.createNoteEntity())
    }

    @Test
    fun `given sortOrder TITLE when getNotes then getNotesByTitle`() = runTest {
        val titleSortOrder = SortOrder.TITLE
        every { noteDao.getNotesByTitle() } returns flowOf(fakeEntityNotes)

        notesRepositoryImpl.getNotes(titleSortOrder).test {
            val emittedDomainNotes = awaitItem()
            assertEquals(1, emittedDomainNotes.size)
            assertEquals(fakeEntityNotes.first().title, emittedDomainNotes.first().title)
            awaitComplete()
        }

        verify(exactly = 1) { noteDao.getNotesByTitle() }
    }

    @Test
    fun `given sortOrder DATE_ASC when getNotes then getNotesByDateAsc`() = runTest {
        val dateAscSortOrder = SortOrder.DATE_ASC
        every { noteDao.getNotesByDateAsc() } returns flowOf(fakeEntityNotes)

        notesRepositoryImpl.getNotes(dateAscSortOrder).test {
            val emittedDomainNotes = awaitItem()
            assertEquals(1, emittedDomainNotes.size)
            assertEquals(fakeEntityNotes.first().id, fakeEntityNotes.first().id)
            awaitComplete()
        }

        verify(exactly = 1) { noteDao.getNotesByDateAsc() }
    }

    @Test
    fun `given sortOrder DATE_DESC when getNotes then getNotesByDATE_DESC`() = runTest {
        val dateDescSortOrder = SortOrder.DATE_DESC
        every { noteDao.getNotesByDateDesc() } returns flowOf(fakeEntityNotes)

        notesRepositoryImpl.getNotes(dateDescSortOrder).test {
            val emittedDomainNotes = awaitItem()
            assertEquals(1, emittedDomainNotes.size)
            assertEquals(fakeEntityNotes.first().title, emittedDomainNotes.first().title)
            awaitComplete()
        }

        verify(exactly = 1) { noteDao.getNotesByDateDesc() }
    }

}