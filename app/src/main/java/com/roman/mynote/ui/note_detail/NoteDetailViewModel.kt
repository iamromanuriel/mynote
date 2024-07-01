package com.roman.mynote.ui.note_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.roman.mynote.mediaplay.AudioPlayManager
import com.roman.mynote.utils.enums.StateInfo
import com.romanuriel.core.Task
import com.romanuriel.domain.model.NoteDetail
import com.romanuriel.domain.usescase.DeleteNoteUseCase
import com.romanuriel.domain.usescase.NoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    noteDetailUseCase: NoteDetailUseCase,
    private val audioPlayManager: AudioPlayManager
): ViewModel() {
    private var stateInfo : StateInfo = StateInfo.SHOW
    private val _task = MutableLiveData<Task<Unit>>()
    val task : LiveData<Task<Unit>>
        get() = _task
    private val _state = MutableLiveData<StateInfo>()

    val progressPlaying = audioPlayManager.getCurrentProgress()
    val statePlaying = audioPlayManager.getState()

    private val note = noteDetailUseCase.invoke().asLiveData()

    val noteDetail: LiveData<Pair<NoteDetail?, StateInfo>> = MediatorLiveData<Pair<NoteDetail?, StateInfo>>().apply {
        addSource(note) { noteDetail ->
            value = Pair(noteDetail, _state.value?:stateInfo)
        }
        addSource(_state) { stateInfo ->
            value = Pair(note.value, stateInfo)
        }
    }


    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _task.postValue(Task.Error(Exception(throwable.localizedMessage)))
        }){

        }
    }

    fun onDeleteById(id: Long){
        viewModelScope
    }

    fun onPlayFile(filePath: String){
        audioPlayManager.play(filePath)
    }

    fun onChangeState(){

    }

}