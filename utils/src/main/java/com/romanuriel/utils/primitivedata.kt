package com.romanuriel.utils

import android.view.View

fun View.isFieldEmpty(value: String): Boolean{
    return value.trim().isEmpty()
}