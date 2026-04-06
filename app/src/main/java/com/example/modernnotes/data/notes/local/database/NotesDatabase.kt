package com.example.modernnotes.data.notes.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.modernnotes.data.notes.local.daos.NoteDao
import com.example.modernnotes.data.notes.local.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}