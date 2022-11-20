package com.roman.mynote.utils

import com.roman.mynote.R
import java.text.SimpleDateFormat
import java.util.*

class UtilsFunctionsNote {
    fun getnumcolor() : Int{
        var colors = listOf<Int>(
            R.color.note_blue,
            R.color.note_green,
            R.color.note_red,
            R.color.note_yellow
        )
        var random = Random()
        var randomColor = random.nextInt(colors.size)

        return colors.get(randomColor)
    }

    fun getDate() : String{
        val sdg = SimpleDateFormat("dd/MM/yyyy")
        return sdg.format(Date()).toString()
    }
}