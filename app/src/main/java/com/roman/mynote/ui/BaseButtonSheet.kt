package com.roman.mynote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.roman.mynote.R

abstract class BaseButtonSheet<B: ViewDataBinding>(val layout: Int): BottomSheetDialogFragment() {
    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent(view, savedInstanceState)
    }

    abstract fun initComponent(view: View, savedInstanceState: Bundle?)

    fun showDialogAlert(message: String, result:(Boolean) -> Unit){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Advertencia")
            .setMessage(message)
            .setIcon(R.drawable.ic_warning)
            .setNegativeButton(android.R.string.cancel){dialog, witch->
                result(false)
            }
            .setPositiveButton(android.R.string.ok){dialog, witch ->
                result(true)
            }
            .show()

    }

}