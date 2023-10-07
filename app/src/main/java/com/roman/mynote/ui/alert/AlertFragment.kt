package com.roman.mynote.ui.alert

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentAlertNotificationsBinding
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.adapter.NoticeAdapter
import com.roman.mynote.utils.set
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertFragment: Fragment(R.layout.fragment_alert_notifications) {
    private val binding: FragmentAlertNotificationsBinding by viewBinding()
    private lateinit var adapterNotice : NoticeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNotice = NoticeAdapter()
        binding.apply {
            setToolbar()
            setRecyclerView()
        }
    }

    private fun FragmentAlertNotificationsBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.notice,
                action = findNavController()::navigateUp,
                endIcon = R.drawable.baseline_more_vert_24
            )
        )
    }


    private fun FragmentAlertNotificationsBinding.setRecyclerView() = this.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = adapterNotice
        adapterNotice.setData(listOf("","","","","",""))
    }


}