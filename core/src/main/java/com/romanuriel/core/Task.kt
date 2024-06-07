package com.romanuriel.core


sealed class Task<out T: Any> {
    data class Success<out T: Any>(val data: T): Task<T>()
    data class Error(val exception: Exception? = null,  var throwable: Throwable? = null): Task<Nothing>()
    object Loading: Task<Unit>()
}