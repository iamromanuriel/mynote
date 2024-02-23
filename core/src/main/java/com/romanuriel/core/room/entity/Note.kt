package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
import com.romanuriel.core.room.model.CoreNote
import java.util.Date

@Entity(tableName = "NOTE",
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
data class Note (
    var content: String = ""
):CoreNote()