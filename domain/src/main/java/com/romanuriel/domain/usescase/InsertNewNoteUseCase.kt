package com.romanuriel.domain.usescase

import com.romanuriel.core.Task
import com.romanuriel.core.room.entity.Note
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import java.util.Date
import javax.inject.Inject

class InsertNewNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(
        title: String = "",
        dateCreate: Long,
        content: String = "",
        type: TypeCategory,
        filePath: String = ""
    ): Task<Unit> {
                val note = Note()
                note.categoryId = type.id
                note.title = title
                note.dateCreate = Date(dateCreate)
                note.lastUpdate = Date()

                when(type){
                    TypeCategory.AUDIO -> {
                        note.audioFilePath = filePath
                    }
                    TypeCategory.REMINDER -> {
                        note.dateTime = Date(dateCreate)
                    }
                    TypeCategory.NOTE ->{
                        note.content = content
                    }
                }

                val query = repository.insert(note)
                return if (query > 0) {
                    Task.Success(Unit)
                } else {
                    Task.Error(Exception("Error to save"))
                }
    }
}