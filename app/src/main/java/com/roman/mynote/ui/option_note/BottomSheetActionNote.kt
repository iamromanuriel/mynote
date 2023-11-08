package com.roman.mynote.ui.option_note

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDialogInfoNoteBinding
import com.roman.mynote.utils.adapter.AdapterOptionNote
import com.romanuriel.utils.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetActionNote() : BaseBottomSheet(R.layout.fragment_dialog_info_note){
    private val binding: FragmentDialogInfoNoteBinding by viewBinding()
    private lateinit var adapterOptionNote : AdapterOptionNote
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("INIT-OPTION-FRAGMENT","")
        adapterOptionNote = AdapterOptionNote {  }

        binding.apply {
            toList()
        }
    }

    private fun FragmentDialogInfoNoteBinding.toList() = this.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = adapterOptionNote
    }
}