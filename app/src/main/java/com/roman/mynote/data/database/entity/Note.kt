package com.roman.mynote.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.roman.mynote.utils.converterdata.ConverterDate
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "note")
@TypeConverters(ConverterDate::class)
data class Note(
    val title: String,
    val note: String,
    val dateCreate: Date,
    val dateLastUpdate: Date,
    val color: Int,
    val pinned: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): Parcelable
