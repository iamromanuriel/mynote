package com.roman.mynote.ui.alertdialog

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.databinding.FragmentDialogInfoNoteBinding
import com.roman.mynote.ui.BaseButtonSheet
import com.roman.mynote.ui.home.HomeViewModel

class BottomSheetActionNote(
    private val viewModel: HomeViewModel,
    private val resultNote: Note,
    private val navigation: NavDirections
) : BaseButtonSheet<FragmentDialogInfoNoteBinding>(R.layout.fragment_dialog_info_note) {

    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        if(resultNote.pinned){binding.buttonPind.setText(R.string.pint)}
        else{binding.buttonPind.setText(R.string.pin)}

        binding.tvContent.text = resultNote.note

        binding.buttonPind.setOnClickListener {
            viewModel.updatePin(resultNote)
            dismiss()
        }

        binding.buttonDelete.setOnClickListener {
            showDialogAlert(getString(R.string.question_delete_note)){
                if(it){ viewModel.deleteNote(resultNote)}
                dismiss()
            }
        }

        binding.buttonEdit.setOnClickListener {
            findNavController().navigate(navigation)
            dismiss()
        }

    }
}