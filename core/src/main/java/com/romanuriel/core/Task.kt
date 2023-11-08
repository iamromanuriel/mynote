package com.romanuriel.core

import android.view.View

sealed class Task<out T: Any> {
    data class Success<out T: Any>(val data: T): Task<T>()
    data class Error(val exception: Exception): Task<Nothing>()
    object Loading: Task<Unit>()
}