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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.utils.DataOption
import com.roman.mynote.utils.adapter.NoteAdapter
import com.roman.mynote.utils.adapter.NoteResultSearchAdapter
import com.roman.mynote.utils.set
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.utils.ResultSearchNoteData
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private val viewModel : HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var adapterNote : NoteAdapter
    private lateinit var adapterResult: NoteResultSearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animEmpty.playAnimation()
        adapterNote = NoteAdapter({ _ ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoteFragment(1))
        }
        ) { id, pin ->
            viewModel.onPin(id, pin)
        }

        adapterResult = NoteResultSearchAdapter {  }

        binding.apply {
            setRecyclerView()
            onRefresh()
            buttonNew()
            manageOptionCreateNote()
        }

        //binding.extendedActionAddNewNote.setOnClickListener(this)
        binding.ivUserProfileImage.setOnClickListener(this)
        binding.ibNotice.setOnClickListener(this)
        observeDataList()
        binding.swipeRefresh.setOnRefreshListener { viewModel.onListNote() }


        observerDataResult()
    }

    private fun observeDataList(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notes.collect{uiState ->
                    binding.progressBar.visibility = View.GONE
                    if(uiState == NoteHomeUiState.Empty) binding.animEmpty.visibility = View.VISIBLE
                    else binding.animEmpty.visibility = View.GONE

                    when(uiState){

                        is NoteHomeUiState.Loading ->{
                        }
                        is NoteHomeUiState.Success ->{
                            adapterNote.setData(uiState.list)

                        }
                        is NoteHomeUiState.Error -> { toast(uiState.msg) }
                        else -> { Log.d("TAG-RESULT-NOTE",uiState.toString()) }
                    }
                }
            }
        }
    }


    private fun observerDataResult(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateNoteResult.collect{ uiState ->

                    when(uiState){
                        is NoteHomeResultUiState.Success ->{


                        }
                        is NoteHomeResultUiState.Empty ->{
                            Log.d("TAG-EMPTY",uiState.toString())
                        }

                        is NoteHomeResultUiState.Loading ->{
                            Log.d("TAG-LOADING",uiState.toString())
                        }
                        else ->{}
                    }
                }
            }
        }
    }

    private fun FragmentHomeBinding.manageOptionCreateNote() = this.layoutFloatingOption.apply {
         set(DataOption(actionNote = { dialog ->  activity.let { dialog.show(it!!.supportFragmentManager, dialog.tag) }},
             actionAudio = { dialog -> activity.let { dialog.show(it!!.supportFragmentManager,dialog.tag) } },
             actionReminder = { dialog ->  findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReminderFragment()) }))
    }

    private fun FragmentHomeBinding.onRefresh() = this.apply {
        swipeRefresh.setOnRefreshListener { viewModel.onListNote() }
    }

    private fun FragmentHomeBinding.setRecyclerView() = this.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = adapterNote
    }
    private fun FragmentHomeBinding.buttonNew() = this.apply {

    }


    override fun onClick(view: View) {
        when (view.id) {

            binding.ivUserProfileImage.id ->{
                val action = findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
            }
            binding.ibNotice.id ->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAlertFragment())
            }
        }
    }


}