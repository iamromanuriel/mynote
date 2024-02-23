package com.romanuriel.core.room.model

import java.util.Date

abstract class CoreNote (
    val id: Int = 0,
    val categoryId: Long = 0,
    val title: String = "",
    val create: Date? = null,
    val update: Date? = null,
    val pin: Boolean? = false
)