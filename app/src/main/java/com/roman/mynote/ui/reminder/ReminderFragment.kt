package com.roman.mynote.ui.reminder

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewReminderBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.Axis
import com.romanuriel.utils.SnackBarLength
import com.romanuriel.utils.dialogTimePickerBasic
import com.romanuriel.utils.showDialog
import com.romanuriel.utils.snackBar
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReminderFragment: BaseFragment(R.layout.fragment_new_reminder, Axis.x) {
    private val binding: FragmentNewReminderBinding by viewBinding()
    private val viewModel: ReminderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            selectTime()
            selectDay()
        }

        viewModel.apply {
            observeTask()
            observerReminderTask()
        }
    }
    private fun FragmentNewReminderBinding.selectTime() = this.apply {
        optionTime.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                dialogTimePickerBasic({hour, minute ->
                    viewModel.setHora(hour, minute)
                },{
                    optionTime.isChecked = false
                })
            }
        }
    }

    private fun FragmentNewReminderBinding.selectDay() = this.apply {
        calendar.minDate = System.currentTimeMillis()
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.setDay(year, month, dayOfMonth)
        }
    }

    private fun FragmentNewReminderBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                titleString = getString(R.string.reminder),
                action = findNavController()::navigateUp,
                textButtonEnd = requireContext().getString(R.string.save),
                buttonEnd = {
                    val title = binding.inputTitle.editText?.text.toString()
                    viewModel.onCreateReminder(title)
                }
            )
        )
    }

    private fun ReminderViewModel.observeTask() = this.apply {
        task.observe(viewLifecycleOwner) { mTask ->
            when (mTask) {
                is Task.Success -> { findNavController().navigateUp() }
                is Task.Error -> {
                    binding.root.snackBar(mTask.exception.localizedMessage ?: "", SnackBarLength.SHORT)
                }
                is Task.Loading ->{  }
                else ->{}
            }
        }
    }

    private fun ReminderViewModel.observerReminderTask() = this.apply {

    }
}