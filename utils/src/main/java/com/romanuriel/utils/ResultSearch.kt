package com.romanuriel.utils

sealed class ResultSearch {
    data class Loading(val value: Boolean): ResultSearch()
    data class Success(val value: MutableList<ResultSearchNoteData>): ResultSearch()
    data class Message(val msg: String): ResultSearch()
}