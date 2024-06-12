package com.romanuriel.domain.repository

import android.util.Log
import com.google.gson.Gson
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
    private var noteItem : NoteItem?= null

    fun setNoteItem(noteItem: NoteItem){
        this.noteItem = noteItem
        Log.d("onSelectNoteItem", Gson().toJson(noteItem))
    }

    fun getNoteItem(): NoteItem?{
        return this.noteItem
    }

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

    fun getNote(): Flow<Note>{
        return db.noteDao().getById(noteItem?.id?:0)
    }

    suspend fun deleteById(id: Long): Int{
        return db.noteDao().deleteById(id)
    }
}