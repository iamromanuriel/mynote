package com.roman.mynote.data.repository

import com.roman.mynote.data.database.Database
import com.roman.mynote.data.database.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository
    @Inject constructor(private val database: Database) {

    fun getListNote(): Flow<List<Note>>{
        return database.noteDao().getListNote()
    }

    suspend fun updateForPin(note: Note){
        val pin = note.pinned
        withContext(Dispatchers.IO){
            val noteUpdate = Note(note.title,note.note,note.dateCreate,note.dateLastUpdate,note.color,pin,note.id)
            database.noteDao().updateNote(noteUpdate)
        }
    }

    suspend fun deleteNote(note: Note){
        withContext(Dispatchers.IO){
            database.noteDao().deleteNote(note)
        }
    }

}