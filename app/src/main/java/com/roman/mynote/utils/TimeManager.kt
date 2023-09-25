package com.roman.mynote.utils

import android.annotation.SuppressLint
import com.roman.mynote.R
import com.roman.mynote.utils.resource.IResourceProvider
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeManager @Inject constructor(
    private val resource: IResourceProvider
) {
    @SuppressLint("SimpleDateFormat")
    fun getTimeAgo(date: Date): String{
        val currentTime = System.currentTimeMillis()
        val inputTime = date.time
        val timeDifference = currentTime - inputTime

        return when{
            timeDifference < 6000 ->{
                val seconds = timeDifference / 1000
                resource.getStringResource(R.string.time_ago)+" "+seconds+" "+if(seconds == 1L) "" else resource.getStringResource(R.string.time_secouds)
            }
            timeDifference < 3600000 -> {
                val minutes = timeDifference / 60000
                resource.getStringResource(R.string.time_ago)+" "+ minutes +" "+if(minutes == 1L) "" else resource.getStringResource(R.string.time_minute)

            }
            isYesterday(date)->{
                resource.getStringResource(R.string.time_yesterday)
            }
            timeDifference < 86400000 -> {
                val hours = timeDifference / 3600000
                resource.getStringResource(R.string.time_ago) +" "+ hours +" "+ if(hours == 1L) "" else resource.getStringResource(R.string.time_hour)
            }
            timeDifference < 2628000000L -> {
                SimpleDateFormat("d MMM").format(date)
            }
            timeDifference < 31536000000L -> {
                SimpleDateFormat("d MMM yy").format(date)
            }
            else -> SimpleDateFormat("dd MMM yy").format(date)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isYesterday(date: Date): Boolean {
        val currentDate = Date()
        val yesterday = Date(currentDate.time - 86400000) // 86400000 ms en un día
        val dayFormat = SimpleDateFormat("yyyyMMdd")
        return dayFormat.format(date) == dayFormat.format(yesterday)
    }
}

