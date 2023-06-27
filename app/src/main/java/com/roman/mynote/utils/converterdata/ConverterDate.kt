package com.roman.mynote.utils.converterdata

import androidx.room.TypeConverter
import java.util.Date

class ConverterDate {
    @TypeConverter
    fun dateToLong(date: Date): Long{
        return date.time
    }
    @TypeConverter
    fun longToDate(timestamp: Long): Date{
        return Date(timestamp)
    }
}