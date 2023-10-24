package com.roman.mynote.ui.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.utils.component.ReminderModel
import com.romanuriel.core.Task
import com.romanuriel.domain.usescase.DeleteNoteUseCase
import com.romanuriel.domain.usescase.NoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val noteDetailUseCase: NoteDetailUseCase
): ViewModel() {
    private var arg = 0L
    private val _task = MutableLiveData<Task<Unit>>()
    val task : LiveData<Task<Unit>>
        get() = _task


    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _task.postValue(Task.Error(Exception(throwable.localizedMessage)))
        }){

        }
    }

    fun setArg(arg: Long){
        this.arg = arg
    }



    fun onDeleteById(id: Long){
        viewModelScope.launch {
            _task.postValue(deleteNoteUseCase.invoke(id))

        }
    }

}