package com.roman.mynote.utils.model

import com.romanuriel.utils.enums.TypeNotice

data class ItemNewModel (
    val id: Long,
    val type: TypeNotice,
    val title: String ? = "",
    val description: String ?=""
)