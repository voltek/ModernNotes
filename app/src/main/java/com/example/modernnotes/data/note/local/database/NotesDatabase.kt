package com.example.modernnotes.data.note.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.modernnotes.data.note.local.daos.NoteDao
import com.example.modernnotes.data.note.local.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}