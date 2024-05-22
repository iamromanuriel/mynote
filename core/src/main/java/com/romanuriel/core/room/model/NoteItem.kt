package com.romanuriel.core.room.model


data class NoteItem(
    val id: Long,
    val categoryId: Long?,
    val title: String,
    val pin: Boolean?
)
