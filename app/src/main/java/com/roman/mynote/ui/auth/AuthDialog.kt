package com.roman.mynote.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAuthBinding
import com.romanuriel.utils.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthDialog : DialogFragment(){
    private lateinit var binding: LayoutAuthBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LayoutAuthBinding.inflate(layoutInflater)
        binding.apply {
            close()
        }

        return MaterialAlertDialogBuilder(
            requireContext()
        ).setView(binding.root).create().also { alertDialog ->
            val window = alertDialog.window
            val wlp: WindowManager.LayoutParams = window?.attributes!!
            wlp.gravity = Gravity.TOP
            window.attributes = wlp
        }
    }


    private fun LayoutAuthBinding.close() = this.apply {
        imageClose.setOnClickListener { dismiss() }
    }
}