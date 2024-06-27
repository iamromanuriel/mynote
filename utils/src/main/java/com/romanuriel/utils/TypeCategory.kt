package com.romanuriel.utils

enum class TypeCategory(val id: Long) {
    AUDIO(2),
    REMINDER(3),
    NOTE(1)
}

fun toIdFromConverter(id: Long): TypeCategory?{
    var mValue : TypeCategory? = null
    for (value in TypeCategory.values()){
        if(value.id == id){
            mValue = value
        }
    }
    return mValue
}

