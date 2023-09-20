package com.romanuriel.core.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.romanuriel.core.room.entity.Category
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.core.room.model.NoteItemResult
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteItemDao {
    @Query("SELECT id,  title FROM NOTE WHERE title LIKE '%' || :text || '%' " +
            "UNION " +
            "SELECT id, title FROM REMINDERS WHERE title LIKE '%' || :text || '%' " +
            "UNION " +
            "SELECT id, title FROM AUDIO_NOTE WHERE title LIKE '%' || :text || '%'")
    fun searchItemByTitle(text: String): Flow<List<NoteItemResult>>


    @Query("SELECT id, categoryId, title, dateCreate AS dataCreate, 0 AS pin FROM NOTE " +
            "UNION " +
            "SELECT id, null AS categoryId, title, datetime AS dataCreate, 0 AS  pin FROM REMINDERS " +
            "UNION " +
            "SELECT id, categoryId, title, dateCreate AS dataCreate, 0 AS pin FROM AUDIO_NOTE")
    fun allNote(): Flow<List<NoteItem>>

    @Query("DELETE FROM NOTE WHERE id =:id")
    suspend fun deleteByIdAndCategory(id: Long): Int

}

