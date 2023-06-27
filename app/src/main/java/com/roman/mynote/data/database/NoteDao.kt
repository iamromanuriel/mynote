package com.roman.mynote.data.database

import androidx.room.*
import com.roman.mynote.data.database.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getListNote(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id =:id")
    fun getNoteById(id: Int): Note

    @Insert
    suspend fun insertNote(vararg note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}