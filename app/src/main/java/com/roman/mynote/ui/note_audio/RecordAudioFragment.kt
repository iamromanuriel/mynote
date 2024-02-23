package com.roman.mynote.ui.note_audio

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutAudioRecordingBinding
import com.roman.mynote.ui.BaseFragment
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.utils.AudioRecorderManager
import com.romanuriel.utils.Axis
import com.romanuriel.utils.dialog
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class RecordAudioFragment : BaseFragment(R.layout.layout_audio_recording, Axis.x){

    private val binding: LayoutAudioRecordingBinding by viewBinding()
    private var grabadora: MediaRecorder? = null
    var ruta : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setToolbar()
            grabar()
            reproducir()
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

    private fun LayoutAudioRecordingBinding.onRecorder() = this.apply {
        AudioRecorderManager(requireActivity().application).apply {
            getRecordingTime().observe(viewLifecycleOwner){ time ->
                textTime.text = time
            }
            btnStart.setOnClickListener {
                startRecording()
            }
        }
    }


    fun LayoutAudioRecordingBinding.grabar() = this.apply{
        btnStart.setOnClickListener {
            if(grabadora == null){
                ruta = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.mp3").absolutePath
                grabadora = MediaRecorder()
                grabadora?.setAudioSource(MediaRecorder.AudioSource.MIC)
                grabadora?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                grabadora?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                grabadora?.setOutputFile(ruta)
                try {
                    grabadora?.prepare()
                    grabadora?.start()
                    btnStart.setBackgroundColor(Color.RED)
                }catch (e: IOException){
                    Log.d("TAG-ERROR-RECORDER",e.localizedMessage)
                }
            }else{
                try{
                    grabadora?.stop()
                    grabadora?.release()
                    btnStart.setBackgroundColor(Color.GREEN)
                }catch (e: IOException){
                    Log.d("TAG-ERROR-RECORDER",e.localizedMessage)
                }
            }
        }

    }

    fun  LayoutAudioRecordingBinding.reproducir() = this.apply {
        btnPause.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            try{
                mediaPlayer.setDataSource(ruta)
                mediaPlayer.prepare()
            }catch (e: IOException){
                println(e.localizedMessage)
            }
            mediaPlayer.start()

        }
    }

}

