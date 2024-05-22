package com.roman.mynote.utils.model

import com.romanuriel.utils.enums.StateNewReminder
import java.util.Calendar
import java.util.Date

data class TimeReminder(
    val hora: Int,
    val min: Int
)
data class DayReminder(
    val day: Int,
    val month: Int,
    val year: Int
)

data class ReminderModel (
    var day: DayReminder? = null,
    var time: TimeReminder? = null,
    var title: String? = null
){
    fun getState(): StateNewReminder{
        return when{
            day == null && time == null && title.isNullOrEmpty() -> StateNewReminder.EMPTY
            day != null && time == null && title.isNullOrEmpty() -> StateNewReminder.HAS_D
            day != null && time != null && title.isNullOrEmpty() -> StateNewReminder.HAS_H
            day != null && time != null && title?.isNotEmpty() == true -> StateNewReminder.HAS_T
            else -> StateNewReminder.ERROR
        }
    }

    fun getDate(): Date {
        val date = Calendar.getInstance()
        when{
            day != null -> {
                date.set(Calendar.DAY_OF_MONTH, day!!.day)
                date.set(Calendar.MONTH, day!!.month)
                date.set(Calendar.YEAR, day!!.year)
            }
            time != null -> {
                date.set(Calendar.HOUR, time!!.hora)
                date.set(Calendar.MINUTE, time!!.min)
                date.set(Calendar.SECOND, 0)
            }
            time == null ->{
                date.set(Calendar.HOUR, 0)
                date.set(Calendar.MINUTE, 0)
                date.set(Calendar.SECOND, 0)
            }
        }
        return date.time
    }
}
