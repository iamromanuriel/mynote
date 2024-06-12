package com.romanuriel.domain.usescase


import com.romanuriel.core.Task
import com.romanuriel.core.room.entity.Note
import com.romanuriel.core.room.model.NoteDetail
import com.romanuriel.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteDetailUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun invoke(): Flow<Note>{
        val task = repository.getNote()
        return task
    }
}