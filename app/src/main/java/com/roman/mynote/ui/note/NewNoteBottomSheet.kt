package com.roman.mynote.ui.note

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import com.roman.mynote.R
import com.roman.mynote.databinding.BottomSheetDialogNewNoteBinding
import com.romanuriel.core.Task
import com.romanuriel.utils.BaseBottomSheet
import com.romanuriel.utils.isFieldEmpty
import com.romanuriel.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_dialog_new_note) {
    private val binding: BottomSheetDialogNewNoteBinding by viewBinding()
    private val viewModel : NewNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            save()
            observeTask()
            close()
        }
    }

    private fun BottomSheetDialogNewNoteBinding.save() = this.apply {
        imageSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            val note = editTextNote.text.toString()

            when {
                root.isFieldEmpty(title) -> { outlinedTextFieldTitle.error = getString(R.string.requeride) }
                root.isFieldEmpty(note) -> { outlinedTextFieldNote.error = getString(R.string.requeride) }
                else ->{ viewModel.newNote(title, note) }
            }
        }
    }
    private fun BottomSheetDialogNewNoteBinding.observeTask() = this.apply {
        viewModel.task.observe(viewLifecycleOwner){task ->
            when(task){
                is Task.Error ->{
                    root.showSnackBar(task.exception.localizedMessage?:"", 12)
                }
                is Task.Success ->{ dismiss() }
            }
        }
    }

    private fun BottomSheetDialogNewNoteBinding.close() = this.apply {
        imageClose.setOnClickListener {
            dismiss()
        }
    }

}