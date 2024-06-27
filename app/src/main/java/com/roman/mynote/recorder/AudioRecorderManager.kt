package com.roman.mynote.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romanuriel.utils.TimeManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

enum class RecordingState{ PASSIVE,STAR, END, CANCEL }

class RecordingStateModel(
    private val isRecording: RecordingState? = RecordingState.PASSIVE,
    private val elapsedTime: String? = "00:00:00",
    private var msg: String? = null
) {
    fun isRecoding(): RecordingState? = isRecording
    fun getElapsedTime(): String? = elapsedTime
    fun msg(): String? = msg
}

class AudioRecorderManager @Inject constructor(
    @ApplicationContext val context: Context,
    val timeManager: TimeManager
) {
    private var _recordingStateModel = MutableLiveData<RecordingStateModel>()
    val recordingStateModel: LiveData<RecordingStateModel> = _recordingStateModel
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
                    _recordingStateModel.postValue(RecordingStateModel(isRecording = RecordingState.PASSIVE, msg = e.message))
                } catch (e: IllegalStateException) {
                    _recordingStateModel.postValue(RecordingStateModel(isRecording = RecordingState.PASSIVE, msg = e.message))
                }
            }

            timer = Timer().apply {
                schedule(object : TimerTask() {
                    private var elapsedTime = 0L
                    override fun run() {
                        elapsedTime += 1000
                        _recordingStateModel.postValue(RecordingStateModel(RecordingState.STAR, timeManager.toTimeCountSimpleHMS(elapsedTime)))
                    }
                }, 0, 1000)
            }
            _recordingStateModel.postValue(RecordingStateModel(RecordingState.STAR))
        }
    }

    fun stop() : String{
        if (isRecording) {
            mediaRecorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: RuntimeException) {
                    val audioDir = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "MyAppAudios")
                    val fileName = "${System.currentTimeMillis()}.mp3"
                    val filePath = File(audioDir, fileName)
                    filePath.delete()
                    _recordingStateModel.postValue(RecordingStateModel(isRecording = RecordingState.END, msg = e.message))
                }
            }
            mediaRecorder = null
            timer?.cancel()
            timer = null
            _recordingStateModel.postValue(RecordingStateModel(RecordingState.END))
        }
        return this.filePath
    }

    fun cancel(){
        if(isRecording){
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            timer?.cancel()
            timer = null
            var file = File(filePath)
            if(file.exists()){
                file.delete()
            }
            _recordingStateModel.postValue(RecordingStateModel(RecordingState.CANCEL))
        }
    }
}