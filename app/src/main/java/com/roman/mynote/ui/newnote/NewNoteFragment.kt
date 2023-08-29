package com.roman.mynote.ui.newnote


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewNoteBinding
import com.roman.mynote.ui.BaseFragment
import com.romanuriel.utils.Axis
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : BaseFragment(R.layout.fragment_new_note, Axis.x) {
    private val viewModel : NewNoteViewModel by viewModels()
    private val binding: FragmentNewNoteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            createNewNote()
        }
    }


    private fun FragmentNewNoteBinding.createNewNote() = this.apply {
        actionButtonSave.setOnClickListener {
            viewModel.newNote(
                binding.textTitle.text.toString(),
                binding.textNote.text.toString()
            )
        }
    }


}