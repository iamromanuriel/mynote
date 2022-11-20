package com.roman.mynote.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
@Entity(tableName = "note")
data class Note(
    val title: String,
    val note: String,
    val date: String,
    val color: Int,
    val pinned: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): Parcelable
