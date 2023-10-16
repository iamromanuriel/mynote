package com.romanuriel.utils

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.util.Log
import androidx.core.app.ActivityCompat
import javax.inject.Inject
import javax.inject.Singleton

class AudioRecorderManager(private val application: Application) {
    private lateinit var mediaRecorder: MediaRecorder
    private var fileName = ""
    fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {

            }catch (e: Throwable){
                Log.d("Exception-media-recorder",e.localizedMessage)
            }
            start()
        }
    }

    fun setName(name: String) {this.fileName = name}

    private fun isRecordingPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            application,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

}