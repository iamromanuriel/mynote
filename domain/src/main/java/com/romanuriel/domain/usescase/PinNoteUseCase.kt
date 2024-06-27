package com.romanuriel.domain.usescase

import com.romanuriel.core.Task
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.repository.NoteRepository
import javax.inject.Inject

class PinNoteUseCase @Inject constructor(
    private val repository: NoteRepository
){
    suspend operator fun invoke(noteItem: NoteItem): Task<Unit>{
        return if(noteItem.pin == false){
            Task.Error(Exception("Note unpinned"))
        } else {
            repository.toPin(noteItem.id, noteItem.categoryId?:0)
            Task.Success(Unit)
        }
    }
}