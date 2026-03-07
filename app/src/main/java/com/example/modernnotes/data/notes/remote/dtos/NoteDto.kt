package com.example.modernnotes.data.notes.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    @SerialName("note_id")
    val id: Int,
    @SerialName("note_title")
    val title: String,
    @SerialName("note_content")
    val content: String,
    @SerialName("note_created_at")
    val createdAt: Long
)