package com.roman.mynote.utils.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class OptionNoteModel(
    val title: String,
    val description: String? = "",
    @DrawableRes val icon: Int? = null,
    @ColorRes val color: Int ? = null
)