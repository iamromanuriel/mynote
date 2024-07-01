package com.roman.mynote.ui.note_detail

import android.view.View
import androidx.annotation.DrawableRes
import com.roman.mynote.databinding.LayoutInfoNoteMainBinding
import com.roman.mynote.utils.enums.StateInfo
import com.romanuriel.utils.TypeCategory


data class CardNoteMainModel(
    val title: String? = "",
    @DrawableRes val icon: Int,
    val category: TypeCategory? = null,
    val create: String? = "",
    val lastUpdate: String? = "",
    val lackOfTime: String? = null,
    val pin : Boolean? = false,
    val state: StateInfo? = StateInfo.SHOW,
    val showDialogTime: () ->Unit = {}
)

fun LayoutInfoNoteMainBinding.set(model: CardNoteMainModel) = this.apply {
    textTitle.text = model.title
    tvCreateDate.text = model.create
    tvLastUpdateDate.text = model.lastUpdate

    when(model.category){
        TypeCategory.AUDIO -> {  }
        TypeCategory.NOTE ->  {

        }
        TypeCategory.REMINDER -> {
            when(model.state){
                StateInfo.EDIT ->{
                    btnTime.visibility = View.VISIBLE
                    btnTime.setOnClickListener { model.showDialogTime() }
                }
                StateInfo.SHOW ->{
                    btnTime.visibility = View.GONE
                }
            }
            model.lackOfTime?.let { tvTimeReminder.text = it }
        }
    }

}