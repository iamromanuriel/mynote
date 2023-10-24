package com.roman.mynote.utils

import android.view.View
import com.roman.mynote.databinding.LayoutButtomSheetDialogOptionBinding
import com.roman.mynote.databinding.LayoutButtonFloattingOptionBinding
import com.roman.mynote.ui.newnote.NewNoteBottomSheet
import com.roman.mynote.ui.note_audio.RecordAudioDialog
import com.roman.mynote.ui.reminder.ReminderDialog
import com.romanuriel.core.room.model.NoteDetail

data class DataOption(
    val actionNote: (noteDialog: NewNoteBottomSheet) -> Unit,
    val actionAudio:(audioDialog: RecordAudioDialog) -> Unit,
    val actionReminder: (reminderDialog: ReminderDialog) -> Unit
)

fun LayoutButtonFloattingOptionBinding.set(data: DataOption) = this.apply {
    var state = false
    mainButtonFloating.setOnClickListener {
        if(state){
            buttonTextIndicatorMain.visibility = View.VISIBLE
            buttonTextIndicatorNote.visibility = View.VISIBLE
            buttonTextIndicatorAudio.visibility = View.VISIBLE
            buttonTextIndicatorReminder.visibility = View.VISIBLE

            noteButtonFloating.visibility = View.VISIBLE
            audioButtonFloating.visibility = View.VISIBLE
            reminderButtonFloating.visibility = View.VISIBLE

            noteButtonFloating.setOnClickListener { data.actionNote(NewNoteBottomSheet()) }
            audioButtonFloating.setOnClickListener { data.actionAudio(RecordAudioDialog()) }
            reminderButtonFloating.setOnClickListener { data.actionReminder(ReminderDialog()) }
        }else{
            buttonTextIndicatorMain.visibility = View.GONE
            buttonTextIndicatorNote.visibility = View.GONE
            buttonTextIndicatorAudio.visibility = View.GONE
            buttonTextIndicatorReminder.visibility = View.GONE

            noteButtonFloating.visibility = View.GONE
            audioButtonFloating.visibility = View.GONE
            reminderButtonFloating.visibility = View.GONE
        }
        state = !state
    }
}