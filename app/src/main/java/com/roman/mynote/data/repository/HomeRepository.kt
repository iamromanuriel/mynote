package com.roman.mynote.data.repository

import androidx.lifecycle.LiveData
import com.roman.mynote.data.database.Database
import com.roman.mynote.data.model.Note
import javax.inject.Inject

class HomeRepository
    @Inject constructor(database: Database) {
    val allNote: LiveData<List<Note>> = database.noteDao().getAllNote()
}