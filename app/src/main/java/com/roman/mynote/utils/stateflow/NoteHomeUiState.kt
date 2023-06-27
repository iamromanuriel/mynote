package com.roman.mynote.utils.stateflow

import com.roman.mynote.data.database.entity.Note

sealed class NoteHomeUiState {
    object Loading: NoteHomeUiState()
    data class Success(val data: List<Note>): NoteHomeUiState()
    data class Error(val msg: String): NoteHomeUiState()
}