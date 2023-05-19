package com.roman.mynote.utils

import com.roman.mynote.R
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UtilsFunctionsNote @Inject constructor(){
    fun getNumColor() : Int{
        val colors = listOf<Int>(
            R.color.md_theme_dark_error,
            R.color.md_theme_dark_errorContainer,
            R.color.md_theme_dark_onPrimary,
            R.color.md_theme_dark_onSurfaceVariant
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