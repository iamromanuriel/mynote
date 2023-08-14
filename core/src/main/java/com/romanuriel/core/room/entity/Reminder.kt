package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "REMINDERS")
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val datetime: Long
)