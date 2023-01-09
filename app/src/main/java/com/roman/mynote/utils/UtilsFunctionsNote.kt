package com.roman.mynote.utils

import com.roman.mynote.R
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UtilsFunctionsNote @Inject constructor(){
    fun getNumColor() : Int{
        val colors = listOf<Int>(
            R.color.note_blue,
            R.color.note_green,
            R.color.note_red,
            R.color.note_yellow
        )
        val random = Random()
        val randomColor = random.nextInt(colors.size)

        return colors.get(randomColor)
    }

    fun getDate() : String{
        val sdg = SimpleDateFormat("dd/MM/yyyy")
        return sdg.format(Date()).toString()
    }
}