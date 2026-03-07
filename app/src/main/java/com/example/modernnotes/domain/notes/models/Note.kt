package com.example.modernnotes.domain.notes.models

data class Note(
    val id: Int?,
    val title: String,
    val content: String,
    val createdAt: Long
)
