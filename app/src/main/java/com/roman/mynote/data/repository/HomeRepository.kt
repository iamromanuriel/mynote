package com.roman.mynote.data.repository

import androidx.lifecycle.LiveData
import com.roman.mynote.data.database.Database
import com.roman.mynote.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository
    @Inject constructor(private val database: Database) {
    val allNote: LiveData<List<Note>> = database.noteDao().getAllNote()

    suspend fun updateForPin(note:Note,pin: Boolean){
        withContext(Dispatchers.IO){
            val noteUpdate = Note(note.title,note.note,note.date,note.color,pin,note.id)
            database.noteDao().updateNote(noteUpdate)
        }
    }

    suspend fun deleteNote(note: Note){
        withContext(Dispatchers.IO){
            database.noteDao().deleteNote(note)
        }
    }

}