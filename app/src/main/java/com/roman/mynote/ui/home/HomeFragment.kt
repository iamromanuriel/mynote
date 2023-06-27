package com.roman.mynote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDialogInfoUserBinding
import com.roman.mynote.utils.adapter.NoteAdapter
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.ui.alertdialog.BottomSheetActionNote
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel : HomeViewModel by viewModels()
    private var adapter : NoteAdapter ? = null

    private fun initRecyclerView(){
        showActionbar(false)


        viewModel.onStart()

        adapter = NoteAdapter({
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoteFragment(it.id))
        },{ note ->
            val buttonSheet = BottomSheetActionNote(viewModel,note,HomeFragmentDirections.actionHomeFragmentToNoteFragment(note.id))
            activity.let { buttonSheet.show(it!!.supportFragmentManager,"TAG") }
        })


        with(binding){
            val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerViewNote.hasFixedSize()
            recyclerViewNote.layoutManager = manager
            recyclerViewNote.adapter = adapter
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.stateNote.collect{uiState->
                        when(uiState){
                            is NoteHomeUiState.Loading ->{
                                binding.loading.visibility = View.VISIBLE
                                binding.animationToEmpty.visibility = View.INVISIBLE
                            }
                            is NoteHomeUiState.Success ->{
                                binding.loading.visibility = View.INVISIBLE
                                if(uiState.data.isNotEmpty()){binding.animationToEmpty.visibility = View.INVISIBLE}
                                adapter?.setListNote(uiState.data)
                            }
                            is NoteHomeUiState.Error ->{
                                binding.loading.visibility = View.INVISIBLE
                                showMessage("Ha ocurrido un error")
                            }
                        }
                    }
                }

            }
        }
    }

    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        viewModel.onStart()
        changeExtendedFabWidthOnVerticalScroll(binding.recyclerViewNote, binding.buttonAddNewNote,300)
        initRecyclerView()
        //inflateAvatarUser()

        initialSearch()
        binding.buttonAddNewNote.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewNoteFragment())
        }
    }

    private fun changeExtendedFabWidthOnVerticalScroll(recyclerView: RecyclerView, fab: ExtendedFloatingActionButton, targetWidth: Int) {
        var isFabWidthChanged = false
        val scrollThreshold = 8 // Umbral de desplazamiento para cambiar el ancho del FAB

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > scrollThreshold && !isFabWidthChanged) {
                    isFabWidthChanged = true

                    fab.post {
                        val layoutParams = fab.layoutParams as ViewGroup.LayoutParams
                        layoutParams.width = fab.height
                        fab.layoutParams = layoutParams
                        fab.text = ""
                    }
                } else if (dy < -scrollThreshold && isFabWidthChanged) {
                    isFabWidthChanged = false

                    fab.post {
                        val layoutParams = fab.layoutParams as ViewGroup.LayoutParams
                        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        fab.layoutParams = layoutParams
                        fab.text = getText(R.string.title_new_note)
                    }
                }
            }
        })
    }

    private fun initialSearch(){
        binding.openSearchView.editText.setOnEditorActionListener { v, actionId, event ->
            val text = binding.openSearchView.editText.text.toString()
            showMessage(text)
            false
        }
    }

    private fun inflateAvatarUser(){
        binding.openSearchBar.inflateMenu(R.menu.menu_search)
        binding.openSearchBar.setOnMenuItemClickListener{


            showDialogInfoUser()
            return@setOnMenuItemClickListener true
        }
    }


    private fun changeSizeButtonFloating(){
        binding.buttonAddNewNote.width = 200
    }

    private fun showDialogInfoUser(){
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_info_user, null)
        val bindingInfoUser = FragmentDialogInfoUserBinding.bind(dialogView)

        MaterialAlertDialogBuilder(requireContext())
            .setView(bindingInfoUser.root)
            .create()
            .show()
    }

}