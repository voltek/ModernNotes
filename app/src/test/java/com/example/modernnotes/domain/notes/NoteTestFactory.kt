package com.example.modernnotes.domain.notes

import com.example.modernnotes.domain.notes.models.Note

object NoteTestFactory {
    const val VALID_TITLE = "Test title note"
    const val VALID_CONTENT = "Test content note"
    const val VALID_TIME_STAMP = 1000L
    const val EMPTY = ""
    const val NOTE_ID = 1

    fun createNote(
        id: Int? = NOTE_ID,
        title: String = VALID_TITLE,
        content: String = VALID_CONTENT,
        timeStamp: Long = VALID_TIME_STAMP
    ) = Note(id, title, content, timeStamp)
}