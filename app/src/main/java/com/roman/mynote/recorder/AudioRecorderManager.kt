package com.roman.mynote.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class RecordingState(
    private val isRecording: Boolean? = null,
    private val elapsedTime: Long? = null,
    private var msg: String? = null
) {
    fun isRecoding(): Boolean? = isRecording
    fun getElapsedTime(): Long? = elapsedTime
    fun msg(): String? = msg
}

class AudioRecorderManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private var _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> = _recordingState
    private var mediaRecorder: MediaRecorder? = null
    private var timer: Timer? = null
    private var isRecording = false
    private var filePath = ""
    fun start() {
        if (!isRecording) {
            isRecording = true
            mediaRecorder = MediaRecorder().apply {
                try {
                    val audioDir = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "MyAppAudios")
                    if (!audioDir.exists()) {
                        val created = audioDir.mkdirs()
                        if (!created) {
                            throw IOException("Failed to create directory: ${audioDir.absolutePath}")
                        }
                    }

                    val fileName = "${System.currentTimeMillis()}.mp3"
                    filePath = File(audioDir, fileName).absolutePath
                    Log.d("CREATEDIR",filePath)
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setOutputFile(filePath)
                    prepare()
                    start()
                } catch (e: IOException) {
                    _recordingState.postValue(RecordingState(isRecording = false, msg = e.message))
                } catch (e: IllegalStateException) {
                    _recordingState.postValue(RecordingState(isRecording = false, msg = e.message))
                }
            }

            timer = Timer().apply {
                schedule(object : TimerTask() {
                    private var elapsedTime = 0L
                    override fun run() {
                        elapsedTime += 1000
                        _recordingState.postValue(RecordingState(isRecording, elapsedTime))
                    }
                }, 0, 1000)
            }
            _recordingState.postValue(RecordingState(isRecording, 0))
        }
    }

    fun stop() : String{
        if (isRecording) {
            isRecording = false
            mediaRecorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: RuntimeException) {
                    val audioDir = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "MyAppAudios")
                    val fileName = "${System.currentTimeMillis()}.mp3"
                    val filePath = File(audioDir, fileName)
                    filePath.delete()
                    _recordingState.postValue(RecordingState(isRecording = false, msg = e.message))
                }
            }
            mediaRecorder = null
            timer?.cancel()
            timer = null
            _recordingState.postValue(RecordingState(isRecording, 0))
        }
        return this.filePath
    }
}