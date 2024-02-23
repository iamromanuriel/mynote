package com.roman.mynote.utils

import android.view.View
import com.roman.mynote.databinding.LayoutButtonFloattingOptionBinding

data class DataOption(
    val actionNote: () -> Unit,
    val actionAudio:() -> Unit,
    val actionReminder: () -> Unit
)

fun LayoutButtonFloattingOptionBinding.set(data: DataOption) = this.apply {
    var visible = false
    startView.setOnClickListener {
        visible = !visible
        if (visible) {
            endView.visibility = View.VISIBLE
            endView.translationY = endView.height.toFloat()

            endView.animate()
                .translationY(0f)
                .setDuration(500)
                .start()
        } else {
            endView.animate()
                .translationY(endView.height.toFloat())
                .setDuration(500)
                .withEndAction { endView.visibility = View.GONE }
                .start()
        }
    }

    fabNote.setOnClickListener { data.actionNote() }
    fabAudio.setOnClickListener { data.actionAudio() }
    fabCalendar.setOnClickListener { data.actionReminder() }
}