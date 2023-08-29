package com.roman.mynote.utils.stateflow

import com.romanuriel.core.room.model.NoteItemResult

sealed class NoteHomeResultUiState {
    object Loading: NoteHomeResultUiState()
    object Empty: NoteHomeResultUiState()
    data class Success(var list: List<NoteItemResult>): NoteHomeResultUiState()
    data class Error(val msg: String): NoteHomeResultUiState()
}