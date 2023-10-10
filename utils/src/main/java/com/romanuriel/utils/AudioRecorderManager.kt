package com.romanuriel.utils

import android.media.MediaRecorder
import android.os.Environment

class AudioRecorderManager {
    private var mediaRecorder: MediaRecorder? = null
    private val outputFile: String
    private val nameFile:String = ""
    init {
        outputFile = Environment.getExternalStorageDirectory().absolutePath + "/$nameFile.3hp"
    }

    fun start(){
        if(mediaRecorder != null){
            throw IllegalArgumentException("La grebacion ya esta en curso.")
        }

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setOutputFile(outputFile)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        mediaRecorder?.prepare()
        mediaRecorder?.start()
    }

    fun stopRecording(){
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    fun getOutputFile(): String{
        return outputFile
    }
}