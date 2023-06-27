package com.roman.mynote.ui.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.ui.NoteViewModel
import com.roman.mynote.ui.note.NoteFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDialog: DialogFragment() {
    private var message = 0
    private var note: Note? = null
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setIcon(android.R.drawable.alert_dark_frame)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(android.R.string.dialog_alert_title)
                .setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        actionConfirm()
                        findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                        dismiss()
                    })
                .setNegativeButton(android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dismiss()
                    })

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setMessage(title: Int){
        this.message = title
    }

    fun setNote(note: Note){
        this.note = note
    }

    private fun actionConfirm(){
        when(message){
            R.string.question_save_note ->{
                viewModel.update(note!!)
            }
            R.string.question_delete_note ->{
                viewModel.delete(note!!)
            }
        }
    }
}