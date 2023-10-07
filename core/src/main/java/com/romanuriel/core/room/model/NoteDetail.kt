package com.romanuriel.core.room.model

data class NoteDetail (
    val id: Long,
    val title: String ? = "",
    val content: String ? = ""
)