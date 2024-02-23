package com.roman.mynote.ui.note_detail

import androidx.annotation.DrawableRes
import com.roman.mynote.databinding.LayoutInfoNoteMainBinding

data class CardNoteMainModel(
    val title: String,
    @DrawableRes val icon: Int
)

fun LayoutInfoNoteMainBinding.set(model: CardNoteMainModel) = this.apply {
    textTitle.text = model.title
}