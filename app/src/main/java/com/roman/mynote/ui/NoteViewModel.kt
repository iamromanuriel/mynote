package com.roman.mynote.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.roman.mynote.data.database.NoteDatabase
import com.roman.mynote.data.database.NoteRepositorie
import com.roman.mynote.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val allNote: LiveData<List<Note>>
    val repositorie: NoteRepositorie

    init{
        val dao = NoteDatabase.getDatabase(application).note()
        repositorie = NoteRepositorie(dao)
        allNote = repositorie.allNote
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repositorie.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repositorie.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repositorie.delete(note)
    }

}