package com.roman.mynote.ui.alert

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentAlertNotificationsBinding
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.adapter.NoticeAdapter
import com.roman.mynote.utils.set
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlertFragment: Fragment(R.layout.fragment_alert_notifications) {
    private val binding: FragmentAlertNotificationsBinding by viewBinding()
    private val viewModel: AlertViewModel by viewModels()
    private lateinit var adapterNotice : NoticeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNotice = NoticeAdapter()
        binding.apply {
            setToolbar()
            setRecyclerView()
        }
        observeData()
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
    }

    private fun observeData(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.dataItemNew.collect{ data ->
                    adapterNotice.setData(data)
                }
            }
        }
    }
}