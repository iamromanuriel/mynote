package com.roman.mynote.ui.notedetail

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDetailNoteBinding
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_detail_note) {
    private val viewModel : NoteDetailViewModel by viewModels()
    private val binding: FragmentDetailNoteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            setDetailMain()
        }
        observerTask()
    }

    private fun FragmentDetailNoteBinding.setDetailMain() = this.layoutDetailMain.apply {
        layoutDetailMain.iconReference.isVisible = true
    }


    private fun FragmentDetailNoteBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.title_detail,
                action = findNavController()::navigateUp,
                endIcon = R.drawable.baseline_more_vert_24
            )
        )
    }

    private fun observerTask(){
        viewModel.task.observe(this.viewLifecycleOwner){task ->
            when(task){
                is Task.Error ->{toast(task.exception.localizedMessage)}
                is Task.Success ->{}
            }
        }
    }

}