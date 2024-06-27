package com.roman.mynote.ui.note_detail


import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDetailNoteBinding
import com.roman.mynote.mediaplay.AudioPlayManager
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.enums.StateInfo
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.domain.model.NoteDetail
import com.romanuriel.utils.TypeCategory
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_detail_note) {
    private val viewModel : NoteDetailViewModel by viewModels()
    private val binding: FragmentDetailNoteBinding by viewBinding()

    @Inject
    lateinit var audioPlayManager: AudioPlayManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
        }
        viewModel.apply {
            onLoadDataNote()
            changeStateInfo()
        }
        observerTask()
    }


    private fun FragmentDetailNoteBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.title_detail,
                action =
                findNavController()::navigateUp,
                buttonEnd = {
                    viewModel.onChangeState(StateInfo.EDIT)
                }
            )
        )
    }

    private fun NoteDetailViewModel.changeStateInfo() = this.apply {
        this.state.observe(viewLifecycleOwner){
            binding.layoutToolbar.buttonEndBasic.visibility = View.VISIBLE
            when(it){
                StateInfo.SHOW -> {
                    binding.layoutToolbar.buttonEndBasic.text = getString(R.string.edit)
                }
                StateInfo.EDIT -> {
                    binding.layoutToolbar.buttonEndBasic.text = getString(R.string.save)
                }
            }
        }
    }

    private fun observerTask(){
        viewModel.task.observe(this.viewLifecycleOwner){ task ->
            when(task){
                is Task.Error ->{ toast(task.exception?.localizedMessage?:"") }
                is Task.Success ->{  }
            }
        }
    }

    private fun NoteDetailViewModel.onLoadDataNote() =this.apply{
        this.note.observe(viewLifecycleOwner){ note ->
            Log.d("noteDetailFlow", Gson().toJson(note))
            binding.includeResultMainNote.set(
                CardNoteMainModel(
                    note?.title,
                    R.drawable.audiowave_85916,
                    note?.category,
                    note?.create,
                    note?.lastUpdate
                )
            )
            viewModel.onChangeState(StateInfo.SHOW)
            if(note?.category == TypeCategory.AUDIO) showCardReproducer(note.filePath )
        }
    }

    private fun showCardReproducer(filePath: String? = null, view: Int? = View.GONE){
        binding.cardPlayAudio.visibility = View.VISIBLE
        binding.includePlayRecord.progressIndicator.setProgressCompat(30, true)
        binding.includePlayRecord.btnPlayPause.setOnClickListener {
            filePath?.let { audioPlayManager.play(it) }
        }
    }

}