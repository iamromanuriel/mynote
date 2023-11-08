package com.roman.mynote.ui.reminder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
import com.romanuriel.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment: BaseFragment(R.layout.fragment_new_reminder, Axis.x) {
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
        var reddy = false

        val parent = calendarView.parent as? ViewGroup
        parent?.removeView(calendarView)

        viewFlipper.addView(calendarView)
        viewFlipper.flipInterval = 200
        viewFlipper.isAutoStart = true

        showComponent(reddy)
        buttonReddy.setOnClickListener {
            reddy = !reddy
            viewFlipper.showNext()
            showComponent(reddy)
        }
        buttonBefore.setOnClickListener {
            reddy = !reddy
            viewFlipper.showPrevious()
            showComponent(reddy)
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
                    if(title.isEmpty()) editTextTitle.error = requireContext().getString(R.string.requeride)
                        else viewModel.onCreateReminder(date, title)
                }
            )
        )
    }

    private fun ReminderViewModel.observeTask() = this.apply {
        task.observe(viewLifecycleOwner) { mTask ->
            when (mTask) {
                is Task.Success -> { findNavController().navigateUp() }

                is Task.Error -> {
                    binding.root.showSnackBar(mTask.exception.localizedMessage ?: "", 50)
                }
                is Task.Loading ->{  }
                else ->{}
            }
        }
    }

    private fun showComponent(isReddy: Boolean){
        binding.apply {
            if(isReddy){
                Log.d("REDDY",isReddy.toString())
                //viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_out_right)
                calendarView.visibility = View.INVISIBLE
                layoutInputTitle.visibility = View.VISIBLE
                buttonReddy.visibility = View.INVISIBLE
                buttonBefore.visibility = View.VISIBLE
            }else{
                Log.d("REDDY",isReddy.toString())
                //viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left)
                calendarView.visibility = View.VISIBLE
                layoutInputTitle.visibility = View.INVISIBLE
                buttonReddy.visibility = View.VISIBLE
                buttonBefore.visibility = View.INVISIBLE
            }
        }
    }

}