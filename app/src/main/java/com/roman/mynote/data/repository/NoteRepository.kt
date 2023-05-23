package com.roman.mynote.data.repository

import androidx.lifecycle.LiveData
import com.roman.mynote.data.database.Database
import com.roman.mynote.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class NoteRepository @Inject constructor(private val database: Database) {
    val allNote: LiveData<List<Note>> = database.noteDao().getAllNote()

    suspend fun getNoteById(id: Int): Note{
        return withContext(Dispatchers.IO){
            database.noteDao().getNoteById(id)
        }
    }

    suspend fun insert(title: String, note: String) {
        withContext(Dispatchers.IO){
            val newNote = Note(title,note, "",2,false,0)
            database.noteDao().insertNote(newNote)
        }
    }
    suspend fun delete(note: Note) = withContext(Dispatchers.IO){
        database.noteDao().deleteNote(note)
    }

    suspend fun update(id: Int, title: String, note: String){
        withContext(Dispatchers.IO){
            val updateNote = Note(title, note,"", 1,false,id)
            database.noteDao().updateNote(updateNote)
        }
    }
}