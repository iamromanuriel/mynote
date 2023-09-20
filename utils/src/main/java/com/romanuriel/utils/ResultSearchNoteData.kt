package com.romanuriel.utils

import android.view.View

data class ResultSearchNoteData (
    val id: Long,
    val content: String,
    val searched: Int ?= View.GONE
)