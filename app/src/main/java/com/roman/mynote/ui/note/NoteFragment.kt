package com.roman.mynote.ui.note

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {
    private val viewModel : NoteDetailViewModel by viewModels()
    private val binding: FragmentNoteBinding by viewBinding()

}