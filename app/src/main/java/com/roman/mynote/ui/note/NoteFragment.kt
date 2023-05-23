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
    private val viewModel : NoteDetailViewModel by viewModels()
    @Inject lateinit var utilsFunctionsNote: UtilsFunctionsNote

    private val argNote: NoteFragmentArgs by navArgs()

    override fun initComponent(view: View, savedInstanceState: Bundle?) {
        showActionbar(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel._note.observe(this.viewLifecycleOwner){
            binding.editTitle.setText(it.title)
            binding.editNote.setText(it.note)
        }
        viewModel.showNote(argNote.argId)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note,menu)

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save ->{
                showDialogConfirm (getString(R.string.question_save_note)) {
                    if (it) {
                        editNote()
                        findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                    }
                }

                true
            }
            R.id.delete ->{
                showDialogAlert(getString(R.string.question_delete_note)){
                    if(it) {
                        viewModel.delete()
                        findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                        showMessageWithSnackbar(getString(R.string.msg_note_deleted))
                    }
                }

                true
            }
            android.R.id.home ->{
                findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToHomeFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editNote(){
        val title = binding.editTitle.text.toString()
        val note = binding.editNote.text.toString()
        viewModel.update(title,note)
    }


}