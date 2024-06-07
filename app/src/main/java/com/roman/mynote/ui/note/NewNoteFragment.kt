package com.roman.mynote.ui.note


import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewNoteBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.Axis
import com.romanuriel.utils.SnackBarLength
import com.romanuriel.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : BaseFragment(R.layout.fragment_new_note, Axis.x) {
    private val viewModel : NewNoteViewModel by viewModels()
    private val binding: FragmentNewNoteBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            observeTask()
        }
    }

    private fun FragmentNewNoteBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.title_new_note,
                action = findNavController()::navigateUp,
                textButtonEnd = requireContext().getString(R.string.save),
                buttonEnd = {
                    val title = binding.textTitle1.text.toString()
                    val content = binding.textContent.text.toString()
                    if(title.isEmpty() || content.isEmpty()){
                        binding.layoutTextContent.error = requireContext().getString(R.string.requeride)
                    }else{
                        viewModel.newNote(title, content)
                    }
                }
            )
        )
    }

    private fun FragmentNewNoteBinding.observeTask() = this.apply {
        viewModel.task.observe(viewLifecycleOwner){ task ->
            when(task){
                is Task.Error -> {
                    root.snackBar(task.exception?.localizedMessage?:"", SnackBarLength.MEDIUM)
                }
                is Task.Success -> { findNavController().navigateUp() }
                is Task.Loading -> { binding.loading.visibility = View.VISIBLE }
            }
        }
    }

}