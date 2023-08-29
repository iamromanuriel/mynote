package com.roman.mynote.utils.stateflow

import com.romanuriel.core.room.model.NoteItem


sealed class NoteHomeUiState {
    object Loading: NoteHomeUiState()
    object Empty: NoteHomeUiState()
    data class Success(var list: List<NoteItem>): NoteHomeUiState()
    data class Error(val msg: String): NoteHomeUiState()
}