package com.roman.mynote.ui.home


import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.ui.option_note.BottomSheetActionNote
import com.roman.mynote.utils.DataOption
import com.roman.mynote.utils.adapter.NoteAdapter
import com.roman.mynote.utils.adapter.NoteResultSearchAdapter
import com.roman.mynote.utils.set
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.utils.SnackBarLength
import com.romanuriel.utils.showSnackBar
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private val viewModel: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var adapterNote: NoteAdapter
    private lateinit var adapterResult: NoteResultSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animEmpty.playAnimation()
        adapterNote = NoteAdapter({ _ ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoteFragment(1))
        }
        ) {
            val dialog = BottomSheetActionNote()
            activity.let { dialog.show(it!!.supportFragmentManager, dialog.tag) }
        }

        adapterResult = NoteResultSearchAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToNoteFragment(1)
            )
        }

        binding.apply {
            setRecyclerView()
            manageOptionCreateNote()
            setSearchView()
        }
        binding.ivSetting.setOnClickListener(this)
        binding.ibNotice.setOnClickListener(this)

        observeDataList()
        observerDataResult()
    }

    private fun observeDataList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect { uiState ->
                    binding.progressBar.visibility = View.GONE
                    if (uiState == NoteHomeUiState.Empty) binding.animEmpty.visibility =
                        View.VISIBLE
                    else binding.animEmpty.visibility = View.GONE

                    when (uiState) {
                        is NoteHomeUiState.Success -> {
                            adapterNote.setData(uiState.list)
                        }
                        is NoteHomeUiState.Error -> {
                            toast(uiState.msg)
                        }
                        else -> {}
                    }
                }
            }
        }
    }


    private fun observerDataResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateNoteResult.collect { uiState ->

                    when (uiState) {
                        is NoteHomeResultUiState.Success -> {
                            adapterResult.setData(uiState.list)
                        }

                        is NoteHomeResultUiState.Error -> {
                            binding.root.showSnackBar(uiState.msg, SnackBarLength.MEDIUM)
                        }

                        is NoteHomeResultUiState.Empty -> {
                            toast("Resultado vacio")
                        }

                        is NoteHomeResultUiState.Loading -> {
                        }
                    }
                }
            }
        }
    }

    private fun FragmentHomeBinding.manageOptionCreateNote() = this.layoutFloatingOption.apply {
        set(
            DataOption(actionNote = { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewNoteFragment()) },
                actionAudio = { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRecordAudioFragment()) },
                actionReminder = { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReminderFragment()) })
        )
    }

    private fun FragmentHomeBinding.setRecyclerView() = this.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = adapterNote
    }

    private fun FragmentHomeBinding.setSearchView() = this.apply {
        includeResult.settingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        includeResult.settingRecyclerview.adapter = adapterResult
        searchView.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearch(text.toString())
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.ivSetting.id -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
            }
            binding.ibNotice.id -> {}
        }
    }
}