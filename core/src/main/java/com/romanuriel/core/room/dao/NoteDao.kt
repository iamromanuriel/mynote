package com.romanuriel.core.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.model.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM NOTE WHERE id =:id")
    fun getById(id: Long): Flow<Note>

    @Insert(onConflict = REPLACE)
    fun insert(note: Note): Long
    @Update
    fun update(note: Note)
    @Delete
    fun delete(note: Note)
    @Query("SELECT id, categoryId, title, pin FROM NOTE")
    fun getToListNote(): Flow<List<NoteItem>>
}