package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
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
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var categoryId: Long? = 0,
    var title: String = "",
    var content: String = "",
    var dateCreate: Date? = null,
    var lastUpdate: Date? = null,
    var pin: Boolean? = false
)