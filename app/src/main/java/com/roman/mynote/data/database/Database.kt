package com.roman.mynote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roman.mynote.data.database.entity.Note

@Database(
    entities = [Note::class],
    version = 2
)
abstract class Database: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}