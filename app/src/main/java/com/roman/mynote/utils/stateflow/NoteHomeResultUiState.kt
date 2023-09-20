package com.roman.mynote.utils.stateflow

import com.romanuriel.core.room.model.NoteItemResult
import com.romanuriel.utils.ResultSearchNoteData

sealed class NoteHomeResultUiState {
    object Loading: NoteHomeResultUiState()
    object Empty: NoteHomeResultUiState()
    data class Success(var list: List<ResultSearchNoteData>): NoteHomeResultUiState()
    data class Error(val msg: String): NoteHomeResultUiState()
}