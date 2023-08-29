package com.roman.mynote.ui.newnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.roman.mynote.R
import com.roman.mynote.databinding.BottomSheetDialogNewNoteBinding
import com.romanuriel.utils.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteBottomSheet : BaseBottomSheet() {
    private lateinit var binding: BottomSheetDialogNewNoteBinding
    private val viewModel : NewNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetDialogNewNoteBinding.inflate(inflater)
        setViews()
        return binding.root
    }

    private fun setViews(){
        binding.apply {
            saveNote.setOnClickListener{
                val title = editTextTitle.text.toString()
                val note = editTextNote.text.toString()
                if (title.isEmpty() || note.isEmpty() ) {
                    outlinedTextFieldTitle.error = getString(R.string.requeride)
                }else{
                    viewModel.newNote(title, note)
                }
            }
        }
    }

}