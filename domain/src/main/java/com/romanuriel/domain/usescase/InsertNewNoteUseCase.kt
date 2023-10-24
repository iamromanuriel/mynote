package com.romanuriel.domain.usescase

import android.util.Log
import com.romanuriel.core.Task
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.entity.Reminder
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import java.util.Date
import javax.inject.Inject

class InsertNewNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(title: String, content: String, type: TypeCategory): Task<Unit>{

        return when(type.id){
            1L ->{Task.Error(Exception("Error"))}
            2L ->{
                val reminder = Reminder(
                    id = 0,
                    title = title,
                    datetime = Date(),
                    dateCreate = Date(),
                    lastUpdate = Date(),
                    pin = false)

                val query = repository.insertReminder(reminder)

                if(query > 0){
                    Task.Success(Unit)
                }else{
                    Task.Error(Exception("Error"))}
                }
            3L -> {

                val note = Note()
                note.categoryId = type.id
                note.title = title
                note.content = content
                note.dateCreate = Date()
                note.lastUpdate = Date()
                Log.d("TAG-CATEGORY", note.toString())
                val query = repository.insert(note)
                if(query >0){
                    Task.Success(Unit)
                }else{
                    Task.Error(Exception("Error"))
                }
            }

            else -> {Task.Error(Exception(""))}
        }
    }
}