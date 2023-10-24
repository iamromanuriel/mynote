package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
import java.util.Date

@Entity(tableName = "REMINDERS",
    indices = [Index(value = ["categoryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ])
@TypeConverters(ConverterDate::class)
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long? = 0,
    val title: String? = "",
    val datetime:Date? = null,
    val dateCreate: Date? = null,
    val lastUpdate: Date? = null,
    val pin: Boolean? = false
)