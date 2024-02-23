package com.romanuriel.domain.usescase

import com.romanuriel.core.Task
import com.romanuriel.core.room.entity.Audio
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.entity.Reminder
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import java.util.Date
import javax.inject.Inject

class InsertNewNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(
        title: String = "",
        dateCreate: Long = 0,
        content: String = "",
        type: TypeCategory
    ): Task<Unit> {

        return when (type) {
            TypeCategory.AUDIO -> {
                val audio = Audio()
                    audio.categoryId = type.id
                    audio.title = title
                    audio.audioFilePath = content
                    audio.create = Date()

                val query = repository.insertAudio(audio)

                if(query > 0){
                    Task.Success(Unit)
                }else{
                    Task.Error(Exception("Error al guardar la nota"))
                }
            }
            TypeCategory.REMINDER -> {
                val reminder = Reminder(
                    categoryId = type.id,
                    title = title,
                    datetime = Date(),
                    dateCreate = Date(dateCreate),
                    lastUpdate = Date()
                )

                val query = repository.insertReminder(reminder)

                if (query > 0) {
                    Task.Success(Unit)
                } else {
                    Task.Error(Exception("Error"))
                }
            }

            TypeCategory.NOTE -> {
                val note = Note()
                note.categoryId = type.id
                note.title = title
                note.content = content
                note.create = Date()
                note.update = Date()

                val query = repository.insert(note)
                if (query > 0) {
                    Task.Success(Unit)
                } else {
                    Task.Error(Exception("Error"))
                }
            }

            else -> {
                Task.Error(Exception(""))
            }
        }
    }
}