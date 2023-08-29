package com.roman.mynote.utils

import com.romanuriel.utils.CategoryType
import java.util.Date

data class NoteItems (
    val id: Long,
    val title: String,
    val lastUpdate: Date,
    val category: CategoryType
)