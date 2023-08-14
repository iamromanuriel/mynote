package com.romanuriel.core.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.romanuriel.core.room.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM REMINDERS WHERE id=:id")
    fun getById(id: Long): Flow<Reminder>
    @Insert(onConflict = REPLACE)
    fun insert(reminder: Reminder)

    @Update
    fun update(reminder: Reminder)
    @Delete
    fun delete(reminder: Reminder)
}