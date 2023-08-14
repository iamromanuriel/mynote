package com.roman.mynote.utils.stateflow


sealed class NoteHomeUiState {
    object Loading: NoteHomeUiState()
    data class Error(val msg: String): NoteHomeUiState()
}