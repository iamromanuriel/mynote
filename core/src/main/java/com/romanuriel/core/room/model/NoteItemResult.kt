package com.romanuriel.core.room.model

data class NoteItemResult (
    val id: Long,
    val title: String,
    val content: String ? = "",
    val category: Int? = 0
)