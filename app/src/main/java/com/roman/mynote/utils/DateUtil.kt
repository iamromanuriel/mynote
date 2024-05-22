package com.roman.mynote.utils

import java.util.Calendar
import java.util.Date

object DateUtil {
    fun dateSimple(): Date{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

}