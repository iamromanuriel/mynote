package com.romanuriel.core.converter

import androidx.room.TypeConverter
import java.util.Date

class ConverterDate {
    @TypeConverter
    fun fromTimesConverter(value: String): Date{
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String?{
        return date?.toString()
    }
}

