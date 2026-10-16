package com.example.modernnotes.data.notes.remote.mappers

import com.example.modernnotes.data.notes.remote.dtos.NoteDto
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteDtoMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        // Arrange
        val dto = NoteDto(
            id = 1,
            title = "Test title",
            content = "Test content",
            createdAt = 1000L
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(dto.id, result.id)
        assertEquals(dto.title, result.title)
        assertEquals(dto.content, result.content)
        assertEquals(dto.createdAt, result.createdAt)
    }
}