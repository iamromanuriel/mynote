package com.roman.mynote.ui.newnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentNewNoteBinding
import com.roman.mynote.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : BaseFragment<FragmentNewNoteBinding>(R.layout.fragment_new_note) {
    private val viewModel : NewNoteViewModel by viewModels()

    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        showActionbar(true)
        setHomeUp(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                findNavController().navigate(NewNoteFragmentDirections.actionNewNoteFragmentToHomeFragment())
                true
            }
            R.id.save_new_note ->{
                saveNewNote()
                findNavController().navigate(NewNoteFragmentDirections.actionNewNoteFragmentToHomeFragment())
                true
            }

            else -> { super.onOptionsItemSelected(item) }
        }

    }

    private fun saveNewNote(){
        val title = binding.editTitle.text.toString()
        val note = binding.editNote.text.toString()

        if(note.isEmpty()){
            binding.inputLayoutTitle.error = getString(R.string.requeride)
            binding.inputLayoutTitle.error = getString(R.string.requeride)
        }else{
            viewModel.newNote(title,note)
        }

    }

}