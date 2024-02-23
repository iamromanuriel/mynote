package com.romanuriel.utils

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class AudioRecorderManager(private val application: Application) {
    private lateinit var mediaRecorder: MediaRecorder
    private var fileName = ""
    private val recordingTime = MutableLiveData<String>()
    private var isRecording = false
    private val handler = android.os.Handler()
    private val updateInterval = 1000L

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRecording) {
                val currentTimeString = recordingTime.value ?: "00:00"
                val parts = currentTimeString.split(":")

                val minutes = parts.getOrElse(0) { "0" }.toLong()
                val seconds = parts.getOrElse(1) { "0" }.toLong()

                val newTime = TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds)

                recordingTime.value = formatTime(newTime)
                handler.postDelayed(this, updateInterval)
            }
        }
    }

    init {
        recordingTime.value = formatTime(0)
    }

    fun startRecording() {
        if(this::mediaRecorder.isInitialized){
            try {
                mediaRecorder.apply {
                    stop()
                    release()
                }
            }catch (e: IOException){
                Log.e("Exception-media-recorder",e.localizedMessage)
            }
        }else{
            mediaRecorder = MediaRecorder().apply {
                fileName = File(application.applicationContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.mp3").absolutePath
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(fileName)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                try {
                    prepare()
                    start()
                    isRecording = true
                    handler.postDelayed(updateRunnable, updateInterval)
                }catch (e: Throwable){

                }
            }
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                TimeUnit.MINUTES.toSeconds(minutes)

        val formattedMinutes = if (minutes >= 0) minutes else 0
        val formattedSeconds = if (seconds >= 0) seconds else 0

        return String.format("%02d:%02d", formattedMinutes, formattedSeconds)
    }

    fun getRecordingTime(): LiveData<String>{
        return recordingTime
    }

    fun setName(name: String) {this.fileName = name}

    fun isRecordingPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            application,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }


}