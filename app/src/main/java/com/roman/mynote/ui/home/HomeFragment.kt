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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.ui.auth.AuthDialog
import com.roman.mynote.ui.newnote.NewNoteBottomSheet
import com.roman.mynote.utils.adapter.NoteAdapter
import com.roman.mynote.utils.adapter.NoteResultSearchAdapter
import com.roman.mynote.utils.adapter.SwipeToLeftCallback
import com.roman.mynote.utils.notification.NotificationHelper
import com.roman.mynote.utils.notification.NotificationModel
import com.roman.mynote.utils.notification.TypeNotification
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.core.Task
import com.romanuriel.utils.ResultSearchNoteData
import com.romanuriel.utils.showSnackBar
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


        //Add Listener MotionLayout
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
            setRecyclerViewSearch()
        }
        binding.extendedActionAddNewNote.setOnClickListener(this)
        binding.ivUserProfileImage.setOnClickListener(this)
        binding.ibNotice.setOnClickListener(this)
        observeDataList()

        adapterResult.setData(listOf<ResultSearchNoteData>(
            ResultSearchNoteData(1,"Nota 1"),
            ResultSearchNoteData(2, "Nota 2"),
            ResultSearchNoteData(3, "Nota 3")
        ))
        binding.includeResult.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())

        observerDataResult()
    }

    private fun observeDataList(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notes.collect{uiState ->
                    when(uiState){
                        is NoteHomeUiState.Loading ->{  }
                        is NoteHomeUiState.Success ->{
                            Log.d("TAG-RESULT-NOTE",uiState.list.toString())
                            adapterNote.setData(uiState.list)
                            val itemTouchHelper = ItemTouchHelper(SwipeToLeftCallback(adapterNote){

                            })
                            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
                        }
                        else -> {}
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

                            adapterResult.setData(listOf<ResultSearchNoteData>(
                                ResultSearchNoteData(1,"Nota 1"),
                                ResultSearchNoteData(2, "Nota 2"),
                                ResultSearchNoteData(3, "Nota 3")
                            ))
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

    private fun observeDataResultTask(){
        viewModel.taskFirebase.observe(this.viewLifecycleOwner){result ->
            when(result){
                is Task.Success ->{ }
                is Task.Error ->{
                    binding.root.showSnackBar(result.exception.localizedMessage?:"",24)
                }
            }
        }
    }

    private fun FragmentHomeBinding.onRefresh() = this.apply {
        swipeRefresh.setOnRefreshListener { viewModel.onListNote() }
    }

    private fun FragmentHomeBinding.setRecyclerView() = this.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = adapterNote
    }

    private fun FragmentHomeBinding.setRecyclerViewSearch() = this.includeResult.recyclerViewSearch.apply{
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = adapterResult
    }


    override fun onClick(view: View) {
        when (view.id) {
            binding.extendedActionAddNewNote.id ->{
                val action = NewNoteBottomSheet()
                activity?.let { action.show(it.supportFragmentManager, action.tag) }
            }

            binding.ivUserProfileImage.id ->{
                val action = AuthDialog()
                activity?.let { action.show(it.supportFragmentManager, action.tag) }
            }
            binding.ibNotice.id ->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAlertFragment())
            }
        }
    }


}