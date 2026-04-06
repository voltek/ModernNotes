package com.example.modernnotes

import com.example.modernnotes.data.notes.local.entities.NoteEntity

object NoteEntityTestFactory {
    const val VALID_TITLE = "Test title note"
    const val VALID_CONTENT = "Test content note"
    const val VALID_TIME_STAMP = 1000L
    const val EMPTY = ""
    const val NOTE_ID = 1

    fun createNote(
        id: Int = NOTE_ID,
        title: String = VALID_TITLE,
        content: String = VALID_CONTENT,
        timeStamp: Long = VALID_TIME_STAMP
    ) = NoteEntity(id, title, content, timeStamp)
}