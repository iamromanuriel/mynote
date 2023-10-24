package com.roman.mynote.ui.note_audio

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAudioRecordingBinding
import com.romanuriel.utils.AudioRecorderManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordAudioDialog : BottomSheetDialogFragment(R.layout.layout_audio_recording){

    private val binding: LayoutAudioRecordingBinding by viewBinding()
    private lateinit var audioRecorderManager: AudioRecorderManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioRecorderManager = AudioRecorderManager(requireActivity().application)
        binding.apply {
            close()
            onRecorder()
        }
    }

    private fun LayoutAudioRecordingBinding.onRecorder() = this.apply {
        if (audioRecorderManager.isRecordingPermissionGranted()) {
            audioRecorderManager.setName("nombre_de_tu_archivo.3gp")
            audioRecorderManager.startRecording()
        } else {

        }

        btnStart.setOnClickListener { audioRecorderManager.startRecording() }


    }


    private fun LayoutAudioRecordingBinding.close() = this.apply {
        buttonClose.setOnClickListener { dismiss() }
    }


}
