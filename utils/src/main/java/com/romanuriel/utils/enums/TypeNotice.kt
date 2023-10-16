package com.romanuriel.utils.enums

import androidx.annotation.DrawableRes
import com.romanuriel.utils.R

enum class TypeNotice(@DrawableRes val icon: Int) {
    NOTIFICATION_NOTE(R.drawable.ic_note),
    REMINDER(R.drawable.calendar_with_a_clock_time_tools_icon_icons_com_56831),
    NEW_FEATURES(R.drawable.ic_idea),
    ALERT(R.drawable.ic_warning)
}