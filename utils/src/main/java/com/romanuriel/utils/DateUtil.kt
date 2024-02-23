package com.romanuriel.utils

import java.util.Calendar
import java.util.Date

object DateUtil {
    fun Calendar.clearTime(){
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    fun currentDate(): Date{
        val calendar = Calendar.getInstance()
        calendar.clearTime()
        return calendar.time
    }
}