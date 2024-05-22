package com.roman.mynote.ui.setting

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentSettingBinding
import com.roman.mynote.utils.ProfileData
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment:Fragment(R.layout.fragment_setting) {

    private val binding: FragmentSettingBinding by viewBinding()
    private val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            setDataProfile()
            setRecyclerview()
        }
    }

    private fun FragmentSettingBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.setting,
                action = findNavController()::navigateUp
            )
        )
    }

    private fun FragmentSettingBinding.setDataProfile() = this.includeProfile.apply {
        set(ProfileData(
            id = 1,
            userName = "Roman",
            moreAction = {}
        ))
    }

    private fun FragmentSettingBinding.setRecyclerview() = this.settingRecyclerview.apply {
        layoutManager = LinearLayoutManager(requireContext())

    }

}