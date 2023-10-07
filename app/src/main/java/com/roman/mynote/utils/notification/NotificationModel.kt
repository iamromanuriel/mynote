package com.roman.mynote.utils.notification

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NotificationModel(
    val title: String,
    val message: String,
    @DrawableRes val icon: Int,
    val type: TypeNotification
)

enum class TypeNotification(){
    DEFAULT,REQUEST
}