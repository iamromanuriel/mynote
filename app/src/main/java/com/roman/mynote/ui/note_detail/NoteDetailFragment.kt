package com.roman.mynote.ui.note_detail


import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDetailNoteBinding
import com.roman.mynote.mediaplay.DataPlaying
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.enums.StateInfo
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.TypeCategory
import com.romanuriel.utils.dialogTimePickerBasic
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_detail_note) {
    private val viewModel : NoteDetailViewModel by viewModels()
    private val binding: FragmentDetailNoteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
        }
        viewModel.apply {
            onLoadDataNote()
            progressPlaying()
            statePlaying()
        }
        observerTask()
    }


    private fun FragmentDetailNoteBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.title_detail,
                action = findNavController()::navigateUp,
                buttonEnd = {
                    viewModel.onChangeState()
                }
            )
        )
    }

    private fun observerTask(){
        viewModel.task.observe(this.viewLifecycleOwner){ task ->
            when(task){
                is Task.Error ->{ toast(task.exception?.localizedMessage?:"") }
                is Task.Success ->{  }
            }
        }
    }

    private fun NoteDetailViewModel.onLoadDataNote() = this.apply{
        this.noteDetail.observe(viewLifecycleOwner){ (noteDetail, stateInfo) ->
            binding.layoutToolbar.buttonEndBasic.visibility = View.VISIBLE
            binding.includeResultMainNote.set(
                CardNoteMainModel(
                    title = noteDetail?.title,
                    icon = R.drawable.audiowave_85916,
                    category =  noteDetail?.category,
                    create =  noteDetail?.create,
                    lastUpdate =  noteDetail?.lastUpdate,
                    lackOfTime = noteDetail?.lackOfTime,
                    pin = noteDetail?.pin,
                    state =  stateInfo,
                    showDialogTime = {
                        dialogTimePickerBasic({_,_ -> },{})
                    }
                )
            )
            when(stateInfo){
                StateInfo.EDIT -> {
                    binding.layoutToolbar.buttonEndBasic.text = getString(R.string.save)
                }
                StateInfo.SHOW -> {
                    binding.layoutToolbar.buttonEndBasic.text = getString(R.string.edit)
                }
            }

            when(noteDetail?.category){
                TypeCategory.NOTE -> {  }
                TypeCategory.REMINDER -> {
                    when(stateInfo){
                        StateInfo.SHOW -> {
                            binding.containerCalendar.visibility = View.GONE
                        }
                        StateInfo.EDIT -> {
                            binding.containerCalendar.visibility = View.VISIBLE
                        }
                    }
                }
                TypeCategory.AUDIO -> {
                    when(stateInfo){
                        StateInfo.SHOW -> showCardReproducer(noteDetail.filePath)
                    }
                }
            }
        }
    }

    private fun NoteDetailViewModel.progressPlaying() = this.apply {
        progressPlaying.observe(viewLifecycleOwner){ count ->
            binding.includePlayRecord.progressIndicator.setProgressCompat(count, true)
        }
    }

    private fun NoteDetailViewModel.statePlaying() = this.apply {
        statePlaying.observe(viewLifecycleOwner){ state->

            when(state){
                is DataPlaying.IDLE -> {
                    Log.d("StatePlayingObservable","onIdle")
                }
                is DataPlaying.PLAYING -> {
                    Log.d("StatePlayingObservable","onPlaying")
                }

                is DataPlaying.Error ->{
                    Log.d("StatePlayingObservable","onError")
                    toast(state.throwable)
                }
            }
        }
    }

    private fun showCardReproducer(filePath: String? = null, view: Int? = View.GONE){
        binding.cardPlayAudio.visibility = View.VISIBLE
        binding.includePlayRecord.btnPlayPause.setOnClickListener {
            filePath?.let { viewModel.onPlayFile(it) }
        }

    }


}