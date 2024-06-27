package com.roman.mynote.mediaplay

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.util.Size
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

sealed class DataPlaying(){
    object PASSIVE: DataPlaying()
    object PLAYING: DataPlaying()
    object SLOW: DataPlaying()
    data class TimeFile(
        var sizeTimeFile: Double,
        var timeStamp: Double
    )
}

@Singleton
class AudioPlayManager @Inject constructor(
    @ApplicationContext val context: Context
){
    private val job= Job()
    private val ioThread = CoroutineScope(Dispatchers.IO)
    private val DataPlaying = MutableLiveData<DataPlaying>()
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var filePath = ""

    fun play(filePath: String){
        ioThread.launch ( CoroutineExceptionHandler { coroutineContext, throwable ->

        } ){
            if(!isRecording){
                isRecording = true
                this@AudioPlayManager.filePath = filePath
                mediaPlayer = MediaPlayer().apply {
                    try {
                        setDataSource(filePath)
                        prepareAsync()
                        setOnPreparedListener { start() }
                        setOnCompletionListener {
                            releasePlayer()
                        }
                        setOnErrorListener { mediaPlayer, i, i2 ->

                            releasePlayer()
                            false }

                    }catch (e: Exception){
                        releasePlayer()
                        Log.d("onPlay onExceptionPlaying",e.localizedMessage)
                    }
                }
            }
        }

    }

    private fun releasePlayer(){
        mediaPlayer?.release()
        isRecording = false
        filePath = ""
        mediaPlayer = null
    }
}