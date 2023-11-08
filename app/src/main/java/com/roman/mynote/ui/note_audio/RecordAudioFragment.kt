package com.roman.mynote.ui.note_audio

import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAudioRecordingBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.utils.AudioRecorderManager
import com.romanuriel.utils.Axis
import com.romanuriel.utils.dialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordAudioFragment : BaseFragment(R.layout.layout_audio_recording, Axis.x){

    private val binding: LayoutAudioRecordingBinding by viewBinding()
    private lateinit var audioRecorderManager: AudioRecorderManager
    private lateinit var mediaRecorder: MediaRecorder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioRecorderManager = AudioRecorderManager(requireActivity().application)
        binding.apply {
            setToolbar()
        }
    }

    private fun LayoutAudioRecordingBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.audio,
                action = {
                    dialog(
                        R.string.title_cancel_record,
                        requireContext().getString(R.string.message_cancel_record),
                        {  },
                        { findNavController().navigateUp() })
                },
                textButtonEnd = requireContext().getString(R.string.save),
                buttonEnd = { }
            )
        )
    }



}
