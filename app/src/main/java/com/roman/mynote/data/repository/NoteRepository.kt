package com.roman.mynote.data.repository

import androidx.lifecycle.LiveData
import com.roman.mynote.data.database.Database
import com.roman.mynote.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(private val database: Database) {
    val allNote: LiveData<List<Note>> = database.noteDao().getAllNote()

    suspend fun insert(note: Note) = withContext(Dispatchers.IO){
        database.noteDao().insertNote(note)
    }
    suspend fun delete(note: Note) = withContext(Dispatchers.IO){
        database.noteDao().deleteNote(note)
    }

    suspend fun update(note: Note) = withContext(Dispatchers.IO){
        database.noteDao().updateNote(note)
    }
}