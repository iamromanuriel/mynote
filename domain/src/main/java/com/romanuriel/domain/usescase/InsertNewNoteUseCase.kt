package com.romanuriel.domain.usescase

import com.romanuriel.core.Task
import com.romanuriel.core.room.entity.Note
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import java.util.Date
import javax.inject.Inject

class InsertNewNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(title: String, content: String, type: TypeCategory): Task<Unit>{

        return when(type.id){
            1L ->{Task.Error(Exception("Error"))}
            2L ->{Task.Error(Exception("Error"))}
            3L -> {
                val note = Note()
                note.categoryId = type.id
                note.title = title
                note.content = content
                note.dateCreate = Date()
                note.lastUpdate = Date()

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