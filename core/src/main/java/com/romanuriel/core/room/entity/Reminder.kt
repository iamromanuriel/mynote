package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
import java.util.Date

@Entity(tableName = "REMINDERS")
@TypeConverters(ConverterDate::class)
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val datetime: Long,
    val dateCreate: Date,
    val lastUpdate: Date,
    val pin: Boolean
)