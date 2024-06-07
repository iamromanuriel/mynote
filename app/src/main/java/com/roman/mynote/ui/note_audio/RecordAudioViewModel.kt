package com.roman.mynote.ui.note_audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.mynote.recorder.AudioRecorderManager
import com.roman.mynote.utils.DateUtil
import com.romanuriel.core.Task
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
    val stateRecording = audioRecorderManager.recordingStateModel
    private val _saveLiveData = MutableLiveData<Task<Unit>>()
    val save: LiveData<Task<Unit>>
        get() = _saveLiveData

    fun starRecording(){
        audioRecorderManager.start()
    }

    fun stopRecording(){
        path = audioRecorderManager.stop()
    }

    fun onSave(title: String = ""){
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _saveLiveData.postValue(Task.Error(throwable = throwable))
        }){
            if(title.isNotEmpty()){
                val result = insertNewNoteUseCase.invoke(
                    title = title,
                    dateCreate = DateUtil.dateSimple().time,
                    type = TypeCategory.AUDIO,
                    filePath = path
                )
                _saveLiveData.postValue(result)
            }
        }
    }

    fun onCancel(){
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _saveLiveData.postValue(Task.Error(throwable = throwable))
        }){
            audioRecorderManager.cancel()
        }
    }
}