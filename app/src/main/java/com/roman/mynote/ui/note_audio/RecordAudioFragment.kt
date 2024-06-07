package com.roman.mynote.ui.note_audio

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAudioRecordingBinding
import com.roman.mynote.recorder.RecordingState
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.DialogInput
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.Axis
import com.romanuriel.utils.dialog
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordAudioFragment : BaseFragment(R.layout.layout_audio_recording, Axis.x){

    private val binding: LayoutAudioRecordingBinding by viewBinding()
    private val viewModel: RecordAudioViewModel by viewModels()
    private var isRecording = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setToolbar()
        }
        binding.btnStart.setOnClickListener{ viewModel.starRecording() }
        binding.btnPause.setOnClickListener { viewModel.stopRecording() }
        viewModel.apply {
            showStateRecorder()
            observeSave()
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1000)
        }

    }

    private fun LayoutAudioRecordingBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.audio,
                action = {
                    if(isRecording){
                        dialog(
                            resTitle = R.string.title_cancel_record,
                            message =  requireContext().getString(R.string.message_cancel_record),
                            cancel = {},
                            ok = { viewModel.onCancel() })
                    } else findNavController().popBackStack()
                }
            )
        )
    }

    private fun RecordAudioViewModel.observeSave() = this.apply {
        save.observe(this@RecordAudioFragment){
            when(it){
                is Task.Success -> {
                    findNavController().popBackStack()
                }
                is Task.Error -> {
                    it.exception?.let { toast(it) }
                    it.throwable?.let { toast(it.message?:"") }
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun RecordAudioViewModel.showStateRecorder() = this.apply {
        stateRecording.observe(this@RecordAudioFragment){ stateRecording ->
            when(stateRecording.isRecoding()){
                RecordingState.PASSIVE -> {  }
                RecordingState.STAR -> {
                    //binding.imageMic.colorIconDraw(android.R.color.holo_green_light)
                    isRecording = true
                    binding.textStatus.visibility = View.VISIBLE
                }
                RecordingState.END -> {
                    isRecording = false
                    DialogInput(requireContext(), title = "Quieres guardar el audio?", ok = {
                        viewModel.onSave(it)
                    }).apply {
                        show()
                    }
                }
                RecordingState.CANCEL -> {
                    findNavController().popBackStack()
                }
            }

            binding.textTime.visibility = View.VISIBLE
            binding.textTime.text = stateRecording.getElapsedTime().toString()
            stateRecording.msg()?.let { toast(it) }

        }
    }
}