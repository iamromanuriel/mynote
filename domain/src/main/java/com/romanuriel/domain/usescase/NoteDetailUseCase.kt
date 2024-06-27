package com.romanuriel.domain.usescase


import com.romanuriel.utils.TimeManager
import com.romanuriel.core.room.entity.Note
import com.romanuriel.domain.model.NoteDetail
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.utils.TypeCategory
import com.romanuriel.utils.toIdFromConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteDetailUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val timeManager: TimeManager
) {
    fun invoke(): Flow<NoteDetail?>{
        val noteEmpty = repository.getNote()
        return noteEmpty.map {
            it?.let { note ->
                NoteDetail(
                    id = note.id,
                    title = note.title,
                    category = toIdFromConverter(note.categoryId),
                    create = note.dateCreate?.let { date -> timeManager.getTimeAgo(date) },
                    lastUpdate = note.lastUpdate?.let { date -> timeManager.getTimeAgo(date) },
                    content = note.content,
                    filePath = note.audioFilePath,
                    pin = note.pin
                )
            }
        }
    }
}