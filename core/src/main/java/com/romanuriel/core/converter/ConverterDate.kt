package com.romanuriel.core.converter

import androidx.room.TypeConverter
import java.util.Date

class ConverterDate {
    @TypeConverter
    fun fromTimesConverter(value: Long?): Date?{
        return if(value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time
    }
}

