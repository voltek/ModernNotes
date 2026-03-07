package com.example.modernnotes.data.notes.remote.mappers

import com.example.modernnotes.data.notes.remote.dtos.NoteDto
import com.example.modernnotes.domain.notes.models.Note

fun NoteDto.toDomain(): Note {
    return Note(
        id,
        title,
        content,
        createdAt
    )
}