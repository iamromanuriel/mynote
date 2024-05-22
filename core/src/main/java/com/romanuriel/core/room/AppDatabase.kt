package com.romanuriel.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romanuriel.core.room.dao.CategoryDao
import com.romanuriel.core.room.dao.NoteDao
import com.romanuriel.core.room.entity.Category
import com.romanuriel.core.room.entity.Note

const val databaseVersion = 1
const val databaseName = "AppDatabase"
@Database(
    entities = [Category::class, Note::class],
    version = databaseVersion,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun noteDao(): NoteDao
}