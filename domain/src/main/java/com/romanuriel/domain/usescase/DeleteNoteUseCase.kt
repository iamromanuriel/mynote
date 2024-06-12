package com.romanuriel.domain.usescase

import com.romanuriel.core.Task
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NoteRepository) {

    suspend operator fun invoke(noteItem: NoteItem): Task<Unit>{
        val task = repository.deleteById(noteItem.id)

        return if (task > 0){
            Task.Success(Unit)
        }else{
            Task.Error(Exception("Error al eliminar"))
        }
    }

}