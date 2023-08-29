package com.romanuriel.domain.usescase

import com.romanuriel.core.room.entity.Note
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import java.util.Date
import javax.inject.Inject

class InsertNewNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(title: String, content: String, type: TypeCategory){

        when(type.id){
            1L ->{}
            2L ->{}
            3L -> {
                val note = Note()
                note.categoryId = type.id
                note.title = title
                note.content = content
                note.dateCreate = Date()
                note.lastUpdate = Date()

                repository.insert(note)
            }
        }
    }
}