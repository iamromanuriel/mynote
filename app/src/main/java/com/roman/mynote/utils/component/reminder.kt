package com.roman.mynote.utils.component

import com.roman.mynote.databinding.LayoutInfoNoteMainBinding

data class ReminderModel(
    val title: String,
    val dateCreate: String,
    val dateEnd: String,
    val timeReminder: String,
    val actionIcon: () -> Unit
)

fun LayoutInfoNoteMainBinding.build(reminder: ReminderModel) = this.apply {
    textTitle.text = reminder.title
    tvCreateDate.text = reminder.dateCreate
    tvEndDate.text = reminder.dateEnd
    tvTimeReminder.text = reminder.timeReminder
    iconReference.setOnClickListener{reminder.actionIcon()}
}