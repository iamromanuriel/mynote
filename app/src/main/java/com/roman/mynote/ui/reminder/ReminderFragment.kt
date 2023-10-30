package com.roman.mynote.ui.reminder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewReminderBinding
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.dialog
import com.romanuriel.utils.showSnackBar
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment: Fragment(R.layout.fragment_new_reminder) {
    private val binding: FragmentNewReminderBinding by viewBinding()
    private val viewModel: ReminderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            readyReminder()
        }

        viewModel.apply {
            observeTask()
        }
    }

    private fun FragmentNewReminderBinding.readyReminder() = this.apply {
        buttonReddy.setOnClickListener {
            cardCalendarView.visibility = View.INVISIBLE
            layoutInputTitle.visibility = View.VISIBLE
        }
    }
    private fun FragmentNewReminderBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.reminder,
                action = findNavController()::navigateUp,
                textButtonEnd = requireContext().getString(R.string.save),
                buttonEnd = {
                    val date = calendarView.date
                    val title = editTextTitle.text.toString()

                    viewModel.onCreateReminder(date, title)
                }
            )
        )
    }

    private fun ReminderViewModel.observeTask() = this.apply {
        task.observe(viewLifecycleOwner) { mTask ->
            when (mTask) {
                is Task.Success -> {
                    Log.d("TAG-RESULT-TASK",mTask.toString())
                    findNavController()::navigateUp
                }

                is Task.Error -> {
                    binding.root.showSnackBar(mTask.exception.localizedMessage ?: "", 50)
                }
            }
        }
    }

}