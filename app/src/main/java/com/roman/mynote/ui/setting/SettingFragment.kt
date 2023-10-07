package com.roman.mynote.ui.setting

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentSettingBinding

class SettingFragment:Fragment(R.layout.fragment_setting) {

    private val binding: FragmentSettingBinding by viewBinding()
    private val viewModel: SettingViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}