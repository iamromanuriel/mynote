package com.romanuriel.core.room.model

import androidx.room.PrimaryKey
import java.util.Date

abstract class CoreNote (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var categoryId : Long = 0,
    var title : String = "",
    var create : Date? = null,
    var update : Date? = null,
    var pin : Boolean = false
)