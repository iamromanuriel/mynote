package com.roman.mynote.utils

import android.view.View
import com.roman.mynote.databinding.LayoutButtonFloattingOptionBinding
import com.roman.mynote.ui.note.NewNoteBottomSheet
import com.roman.mynote.ui.note_audio.RecordAudioFragment
import com.roman.mynote.ui.reminder.ReminderFragment

data class DataOption(
    val actionNote: (noteDialog: NewNoteBottomSheet) -> Unit,
    val actionAudio:(audioDialog: RecordAudioFragment) -> Unit,
    val actionReminder: (reminderFragment: ReminderFragment) -> Unit
)

fun LayoutButtonFloattingOptionBinding.set(data: DataOption) = this.apply {
    var state = false
    mainButtonFloating.setOnClickListener {
        state = !state
        if(state){
            buttonTextIndicatorMain.visibility = View.VISIBLE
            buttonTextIndicatorNote.visibility = View.VISIBLE
            buttonTextIndicatorAudio.visibility = View.VISIBLE
            buttonTextIndicatorReminder.visibility = View.VISIBLE

            noteButtonFloating.visibility = View.VISIBLE
            audioButtonFloating.visibility = View.VISIBLE
            reminderButtonFloating.visibility = View.VISIBLE

            noteButtonFloating.setOnClickListener { state = !state
                data.actionNote(NewNoteBottomSheet()) }
            audioButtonFloating.setOnClickListener { state = !state
                data.actionAudio(RecordAudioFragment()) }
            reminderButtonFloating.setOnClickListener { state = !state
                data.actionReminder(ReminderFragment()) }
        }else{
            buttonTextIndicatorMain.visibility = View.GONE
            buttonTextIndicatorNote.visibility = View.GONE
            buttonTextIndicatorAudio.visibility = View.GONE
            buttonTextIndicatorReminder.visibility = View.GONE

            noteButtonFloating.visibility = View.GONE
            audioButtonFloating.visibility = View.GONE
            reminderButtonFloating.visibility = View.GONE
        }
    }
}