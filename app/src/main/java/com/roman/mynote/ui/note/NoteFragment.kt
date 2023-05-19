package com.roman.mynote.ui.note

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.roman.mynote.R
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.FragmentNoteBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.ui.NoteViewModel
import com.roman.mynote.ui.alertdialog.ConfirmDialog
import com.roman.mynote.utils.UtilsFunctionsNote
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BaseFragment<FragmentNoteBinding>(R.layout.fragment_note) {
    private val viewModel : NoteViewModel by viewModels()
    @Inject lateinit var utilsFunctionsNote: UtilsFunctionsNote

    private val argNote: NoteFragmentArgs by navArgs()

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note,menu)
        if(!valueArgs()){
            menu.findItem(R.id.delete).setVisible(false)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save ->{
                if(valueArgs()){
                     showDialogConfirm { if(it) createNote() }
                }else{
                    viewModel.insert(createNote())
                    findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                }

                true
            }
            R.id.delete ->{
                true
            }
            android.R.id.home ->{
                findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun valueArgs(): Boolean{
        return if(argNote.argNote?.id != null) true else false
    }

    private fun createNote(): Note{


        return if(valueArgs()){
            Note(
                binding.editTitle.text.toString(),
                binding.editNote.text.toString(),
                utilsFunctionsNote.getDate(),
                utilsFunctionsNote.getNumColor(),
                argNote.argNote!!.pinned,
                argNote.argNote!!.id
            )
        }else{
            Note(
                binding.editTitle.text.toString(),
                binding.editNote.text.toString(),
                utilsFunctionsNote.getDate(),
                utilsFunctionsNote.getNumColor(),
                false
            )
        }
    }

    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if(valueArgs()){
            (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.title_new_note)
            binding.editTitle.setText(argNote.argNote?.title)
            binding.editNote.setText(argNote.argNote?.note)
        }else{
            (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.title_edit_note)
        }
    }
}