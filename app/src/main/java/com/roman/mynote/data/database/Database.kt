package com.roman.mynote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roman.mynote.data.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}