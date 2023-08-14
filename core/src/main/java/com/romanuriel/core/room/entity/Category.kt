package com.romanuriel.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)