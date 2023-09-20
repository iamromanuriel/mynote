package com.roman.mynote.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAuthBinding
import com.romanuriel.utils.BaseDialogFragment
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class AuthDialog : DialogFragment(){
    private lateinit var binding: LayoutAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LayoutAuthBinding.inflate(layoutInflater)

        binding.apply {
            close()
            showDataToMode()
            changeMode()
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


    private fun LayoutAuthBinding.changeMode() = this.apply {
        textAuth.setOnClickListener {
            val isVisible = outlinedTextFieldPasswordRepeat.visibility == View.VISIBLE
            TransitionManager.beginDelayedTransition(root, ChangeBounds().apply {
                duration = 300
                interpolator = AccelerateDecelerateInterpolator()
            })
            outlinedTextFieldPasswordRepeat.visibility = if (isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun LayoutAuthBinding.showDataToMode() = this.apply {
        if(outlinedTextFieldPasswordRepeat.visibility == View.VISIBLE){
            //Sign up
            idTextSubtitle.text = getString(R.string.sign_up)
            actionAuth.text = getString(R.string.sign_up)
            textAuth.text = getString(R.string.sign_in)
        }else{
            //Sign in
            idTextSubtitle.text = getString(R.string.sign_in)
            actionAuth.text = getString(R.string.sign_in)
            textAuth.text = getString(R.string.sign_up)
        }
    }



    private fun LayoutAuthBinding.close() = this.apply {
        imageClose.setOnClickListener { dismiss() }
    }
}