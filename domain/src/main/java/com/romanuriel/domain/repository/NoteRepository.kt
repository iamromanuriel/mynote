package com.romanuriel.domain.repository

import com.romanuriel.core.room.AppDatabase
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.model.NoteDetail
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.core.room.model.NoteItemResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepository @Inject constructor(
    private val db: AppDatabase
) {

    /*fun searchNoteByTitle(
        text: String
    ): Flow<List<NoteItemResult>> {
        return db.noteItemDao().searchItemByTitle(text)
    }*/

    fun insert(note: Note): Long{
        return db.noteDao().insert(note)
    }

    fun toListNote(): Flow<List<NoteItem>>{
        return db.noteDao().getToListNote()
    }
}