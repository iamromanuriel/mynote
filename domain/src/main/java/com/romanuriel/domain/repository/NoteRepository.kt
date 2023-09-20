package com.romanuriel.domain.repository

import com.romanuriel.core.room.AppDatabase
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.core.room.model.NoteItemResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepository @Inject constructor(
    private val db: AppDatabase
) {

    fun searchNoteByTitle(
        text: String
    ): Flow<List<NoteItemResult>> {
        return db.noteItemDao().searchItemByTitle(text)
    }


    fun getAllNote(): Flow<List<NoteItem>>{
        return db.noteItemDao().allNote()
    }

    fun insert(note: Note): Long{
        return db.noteDao().insert(note)
    }

    suspend fun deleteById(id: Long): Int{
        return db.noteItemDao().deleteByIdAndCategory(id)
    }

}