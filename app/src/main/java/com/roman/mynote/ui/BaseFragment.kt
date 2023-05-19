package com.roman.mynote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.roman.mynote.R

abstract class BaseFragment <B: ViewDataBinding>(val layout: Int): Fragment() {
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layout,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent(view,savedInstanceState)
    }

    abstract fun initComponent(view: View,savedInstanceState: Bundle?)

    fun showDialogConfirm(result : (Boolean) -> Unit){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Advertencia")
            .setMessage("¿Estas seguro de ...?")
            .setNegativeButton(android.R.string.cancel){dialog, witch->
                result(false)
            }
            .setPositiveButton(android.R.string.ok){dialog, witch ->
                result(true)
            }
            .show()
    }
}