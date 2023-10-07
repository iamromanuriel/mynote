package com.romanuriel.domain.usescase

import com.romanuriel.core.room.model.NoteDetail
import com.romanuriel.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteDetailUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: Long): Flow<NoteDetail> {
        return repository.getNoteDetailById(id)
    }
}