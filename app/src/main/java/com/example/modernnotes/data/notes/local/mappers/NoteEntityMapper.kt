package com.example.modernnotes.data.notes.local.mappers

import com.example.modernnotes.core.orDefault
import com.example.modernnotes.data.notes.local.entities.NoteEntity
import com.example.modernnotes.domain.notes.models.Note

fun NoteEntity?.toDomain(): Note {
    return Note(
        this?.id.orDefault(),
        this?.title.orEmpty(),
        this?.content.orEmpty(),
        this?.createdAt ?: 0L
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id ?: 0,
        title,
        content,
        createdAt
    )
}


fun List<NoteEntity>.toDomain(): List<Note> = this.map { it.toDomain() }

fun List<Note>.toEntity(): List<NoteEntity> = this.map { it.toEntity() }