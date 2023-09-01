package com.roman.mynote.ui.home


import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.ui.newnote.NewNoteBottomSheet
import com.roman.mynote.utils.adapter.NoteAdapter
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.core.Task
import com.romanuriel.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private val viewModel : HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var adapterNote : NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNote = NoteAdapter ({ noteItem ->

        }
        ) { id, pin ->
            viewModel.onPin(id, pin)
        }
        binding.apply {
            setRecyclerView()
        }
        binding.extendedActionAddNewNote.setOnClickListener(this)
        observeDataList()
        observeDataResultTask()
    }

    private fun observeDataList(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notes.collect{uiState ->
                    when(uiState){
                        is NoteHomeUiState.Loading ->{  }
                        is NoteHomeUiState.Success ->{
                            Log.d("TAG-LIST-NOTE","${uiState.list}")
                            adapterNote.setData(uiState.list)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeDataResultTask(){
        viewModel.taskFirebase.observe(this.viewLifecycleOwner){result ->
            when(result){
                is Task.Success ->{

                }
                is Task.Error ->{
                    binding.root.showSnackBar(result.exception.localizedMessage?:"",24)
                }
            }
        }
    }

    private fun FragmentHomeBinding.setRecyclerView() = this.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = adapterNote
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.extendedActionAddNewNote.id ->{
                val action = NewNoteBottomSheet()
                activity?.let { action.show(it.supportFragmentManager, action.tag) }
            }
        }
    }


}