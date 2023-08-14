package com.roman.mynote.ui.newnote


import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {
    private val viewModel : NewNoteViewModel by viewModels()
    private val binding: FragmentNewNoteBinding by viewBinding()

}