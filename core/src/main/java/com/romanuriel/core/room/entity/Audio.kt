package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
import java.util.Date

@Entity(
    tableName = "AUDIO_NOTE",
    indices = [Index(value = ["categoryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
@TypeConverters(ConverterDate::class)
data class Audio (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long?,
    val title: String,
    val audioFilePath: String,
    val dateCreate: Date,
    val pin: Boolean
)