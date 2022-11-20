package com.roman.mynote.data.database

import androidx.lifecycle.LiveData
import com.roman.mynote.data.model.Note

class NoteRepositorie(private val noteDao: NoteDao) {
    val allNote: LiveData<List<Note>> = noteDao.getAllNote()

    suspend fun insert(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun delete(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun update(note: Note){
        noteDao.updateNote(note)
    }
}