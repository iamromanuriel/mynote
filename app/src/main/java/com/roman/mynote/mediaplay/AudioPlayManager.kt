package com.roman.mynote.mediaplay

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.provider.ContactsContract.Data
import android.util.Log
import android.util.Size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

sealed class DataPlaying{
    object IDLE: DataPlaying()
    object PLAYING: DataPlaying()
    object PAUSED: DataPlaying()
    data class Error(val throwable: Throwable): DataPlaying()
}

@ViewModelScoped
class AudioPlayManager @Inject constructor(
    @ApplicationContext val context: Context
){
    private val job= Job()
    private val ioThread = CoroutineScope(Dispatchers.IO)
    private val currentState = MutableLiveData<DataPlaying>()
    private val progressTracker = MutableLiveData<Int>()
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var progressUpdater: ProgressUpdater? = null
    private var filePath = ""

    fun play(filePath: String){
        if(currentState.value == DataPlaying.PLAYING){
            stop()
        }

        currentState.value = DataPlaying.IDLE

        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(filePath)
                prepareAsync()
                setOnPreparedListener {
                    start()
                    currentState.value = DataPlaying.PLAYING
                    isRecording = true
                    Log.d("onActionPlay","prepared")
                    startProgressTracker()
                }
                setOnCompletionListener {
                    releasePlayer()
                    stopProgressTracker()
                    Log.d("onActionPlay","Completation")
                }
                setOnErrorListener { mediaPlayer, i, i2 ->
                    releasePlayer()
                    currentState.postValue(DataPlaying.Error(Exception("Playback error")))
                    stopProgressTracker()
                    false }

            }catch (e: Exception){
                releasePlayer()
                currentState.value = DataPlaying.Error(Exception("Error preparing media player"))
                currentState.postValue(DataPlaying.Error(e))
            }
        }
        this.filePath = filePath
    }

    fun stop (){
        mediaPlayer?.apply {
            if(isPlaying){
                stop()
            }
            release()
        }
        currentState.value = DataPlaying.IDLE
        mediaPlayer = null
        isRecording = false
        progressTracker.value = 0
        stopProgressTracker()
    }

    fun getCurrentProgress(): LiveData<Int>{
        return progressTracker
    }

    fun getState(): LiveData<DataPlaying>{
        return currentState
    }

    fun getCurrentFilePath(): String{
        return filePath
    }
    private fun startProgressTracker(){
        progressUpdater?.cancel()
        progressUpdater = ProgressUpdater()
        progressUpdater?.start()
    }

    fun stopProgressTracker(){
        progressUpdater?.cancel()
        progressUpdater = null
    }

    private fun releasePlayer(){
        mediaPlayer?.release()
        isRecording = false
        filePath = ""
        mediaPlayer = null
        progressTracker.postValue(0)
    }

    private inner class ProgressUpdater : CountDownTimer(mediaPlayer?.duration?.toLong() ?: 0L, 100L){
        override fun onTick(p0: Long) {
            mediaPlayer?.let {
                val progress = (it.currentPosition.toFloat() / it.duration.toFloat() * 100).toInt()
                Log.d("onActionPlay","secount $progress")
                progressTracker.postValue(progress)
            }
        }

        override fun onFinish() {
            progressTracker.postValue(100)
        }

    }
}