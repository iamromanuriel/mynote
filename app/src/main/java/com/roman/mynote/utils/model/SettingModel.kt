package com.roman.mynote.utils.model

import androidx.annotation.DrawableRes

data class SettingModel (
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val draw: Int
)