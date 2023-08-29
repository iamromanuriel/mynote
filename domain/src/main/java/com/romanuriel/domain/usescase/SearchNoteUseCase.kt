package com.romanuriel.domain.usescase

import com.romanuriel.core.room.model.NoteItemResult
import com.romanuriel.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    operator fun invoke(query: String): Flow<List<NoteItemResult>> {
        return repository.searchNoteByTitle(query)
    }
}