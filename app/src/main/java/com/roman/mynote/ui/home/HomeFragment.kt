package com.roman.mynote.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.roman.mynote.R
import com.roman.mynote.adapter.NoteAdapter
import com.roman.mynote.databinding.FragmentHomeBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.ui.main_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel : HomeViewModel by viewModels()

    private fun initRecyclerView(){
        val adapter = NoteAdapter{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoteFragment(it))
        }
        with(binding){
            viewModel.allNote.observe(viewLifecycleOwner) { result ->
                adapter.setListNote(result)
            }
            val manager = GridLayoutManager(context, 2)
            recyclerNote.hasFixedSize()
            recyclerNote.layoutManager = manager
            recyclerNote.adapter = adapter
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search,menu)
    }



    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoteFragment(null))
        }
    }

}