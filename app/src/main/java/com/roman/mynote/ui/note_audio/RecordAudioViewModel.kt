package com.roman.mynote.ui.note_audio

import androidx.lifecycle.ViewModel
import com.roman.mynote.recorder.AudioRecorderManager
import com.roman.mynote.utils.DateUtil
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import com.romanuriel.utils.TypeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordAudioViewModel @Inject constructor(
    private val audioRecorderManager: AudioRecorderManager,
    private val insertNewNoteUseCase: InsertNewNoteUseCase
): ViewModel() {
    private val ioThread = CoroutineScope(Dispatchers.IO)
    private var path = ""
    val stateRecording = audioRecorderManager.recordingState

    fun starRecording(){
        audioRecorderManager.start()
    }

    fun stopRecording(){
        path = audioRecorderManager.stop()
        onSave()
    }

    private fun onSave(title: String = "Nueva audio"){
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->

        }){
            if(title.isNotEmpty()){
                val result = insertNewNoteUseCase.invoke(
                    title = title,
                    dateCreate = DateUtil.dateSimple().time,
                    type = TypeCategory.AUDIO,
                    filePath = path
                )
            }
        }
    }
}