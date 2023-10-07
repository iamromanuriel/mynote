package com.romanuriel.core.room.model


data class NoteItem(
    val id: Long,
    val categoryId: Long?,
    val title: String,
    val dataCreate: Long,
    val content: String? = "",
    var pin: Boolean
)