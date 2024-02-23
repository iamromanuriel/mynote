package com.roman.mynote.ui.reminder

import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewReminderBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.Axis
import com.romanuriel.utils.SnackBarLength
import com.romanuriel.utils.enums.StateNewReminder
import com.romanuriel.utils.showDialog
import com.romanuriel.utils.showSnackBar
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class ReminderFragment: BaseFragment(R.layout.fragment_new_reminder, Axis.x) {
    private val binding: FragmentNewReminderBinding by viewBinding()
    private val viewModel: ReminderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
        }

        viewModel.apply {
            observeTask()
            observerReminderTask()
        }
    }
    private fun dialogTimePicker(): MaterialTimePicker{
        return MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText(getString(R.string.title_picker_time))
            .build()
    }

    private fun FragmentNewReminderBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                titleString = getString(R.string.reminder),
                action = findNavController()::navigateUp,
                textButtonEnd = requireContext().getString(R.string.save),
                buttonEnd = { viewModel.onCreateReminder(binding.editTextTitle.text.toString()) }
            )
        )
    }

    private fun ReminderViewModel.observeTask() = this.apply {
        task.observe(viewLifecycleOwner) { mTask ->
            when (mTask) {
                is Task.Success -> { findNavController().navigateUp() }
                is Task.Error -> {
                    binding.root.showSnackBar(mTask.exception.localizedMessage ?: "", SnackBarLength.SHORT)
                }
                is Task.Loading ->{  }
                else ->{}
            }
        }
    }

    private fun ReminderViewModel.observerReminderTask() = this.apply {
        stateProgress.observe(viewLifecycleOwner){ mReminder ->
            when(mReminder){
                StateNewReminder.EMPTY ->{
                    binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        viewModel.setDay(year, month, dayOfMonth)
                    }
                }
                StateNewReminder.HAS_D ->{
                    val timerDialog = dialogTimePicker()

                    showDialog(timerDialog)
                    timerDialog.addOnPositiveButtonClickListener {
                        val hour = timerDialog.hour
                        val minute = timerDialog.minute

                        viewModel.setHora(hour, minute)
                    }
                }
                StateNewReminder.HAS_H ->{
                    binding.calendarView.visibility = View.INVISIBLE
                    binding.layoutInputTitle.visibility = View.VISIBLE
                    binding.editTextTitle.visibility = View.VISIBLE
                }
                StateNewReminder.HAS_T ->{  }
            }
        }
    }
}