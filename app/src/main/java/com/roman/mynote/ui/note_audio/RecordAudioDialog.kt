package com.roman.mynote.ui.note_audio

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAudioRecordingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordAudioDialog : BottomSheetDialogFragment(R.layout.layout_audio_recording){

    private val binding: LayoutAudioRecordingBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
