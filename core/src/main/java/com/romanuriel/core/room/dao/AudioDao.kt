package com.romanuriel.core.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.romanuriel.core.room.entity.Audio
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao {
    @Query("SELECT * FROM AUDIO_NOTE WHERE id =:id")
    fun getById(id: Long): Flow<Audio>
    @Insert(onConflict = REPLACE)
    fun insert(audio: Audio)
    @Update
    fun update(audio: Audio)
    @Delete
    fun delete(audio: Audio)
}