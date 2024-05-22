package com.romanuriel.domain.usescase

import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToListAllNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<NoteItem>>{
        return repository.toListNote()
    }
}