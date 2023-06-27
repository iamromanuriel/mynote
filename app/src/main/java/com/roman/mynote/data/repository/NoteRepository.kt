package com.roman.mynote.data.repository

import android.content.Context
import com.roman.mynote.data.database.Database
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.utils.resource.IResourceProvider
import com.roman.mynote.utils.resource.ResourceProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

class NoteRepository @Inject constructor(
    private val database: Database,
    private val resourceProvider: IResourceProvider
    ) {

    suspend fun getNoteById(id: Int): Note {
        return withContext(Dispatchers.IO){
            database.noteDao().getNoteById(id)
        }
    }

    suspend fun insert(title: String, note: String) {
        withContext(Dispatchers.IO){
            val newNote = Note(title,note, Date(),Date(),0,false,0)
            database.noteDao().insertNote(newNote)
        }
    }
    suspend fun delete(note: Note) = withContext(Dispatchers.IO){
        database.noteDao().deleteNote(note)
    }

    suspend fun update(id: Int, title: String, note: String){
        withContext(Dispatchers.IO){
            val updateNote = Note(title, note, Date(), Date(),1,false,id)
            database.noteDao().updateNote(updateNote)
        }
    }

    suspend fun getListNames(): List<String>{
        return listOf("Rojo","Amarillo","Azul","Blanco","Verde","Colorado")
    }

    suspend fun getDaysWeek(): List<String>{
        return listOf("Lunes","Martes","Miercoles","Jueves","Viernes")
    }

}
