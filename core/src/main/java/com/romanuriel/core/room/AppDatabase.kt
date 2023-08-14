package com.romanuriel.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romanuriel.core.room.dao.AudioDao
import com.romanuriel.core.room.dao.CategoryDao
import com.romanuriel.core.room.dao.NoteDao
import com.romanuriel.core.room.dao.ReminderDao
import com.romanuriel.core.room.entity.Audio
import com.romanuriel.core.room.entity.Category
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.entity.Reminder

const val databaseVersion = 1
const val databaseName = "AppDatabase"
@Database(
    entities = [Audio::class,Category::class,Note::class, Reminder::class],
    version = databaseVersion,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun audioDao(): AudioDao
    abstract fun categoryDao(): CategoryDao
    abstract fun noteDao(): NoteDao
    abstract fun reminderDao(): ReminderDao
}