package com.roman.mynote.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.core.Task
import com.romanuriel.core.firebase.InsertNoteFirebase
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.usescase.SearchNoteUseCase
import com.romanuriel.domain.usescase.ToListAllNoteUseCase
import com.romanuriel.utils.ResultSearchNoteData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUseCase: SearchNoteUseCase,
    private val toListAllNoteUseCase: ToListAllNoteUseCase,
    private val insertNoteFirebase: InsertNoteFirebase
): ViewModel() {
    private val listResult = listOf<ResultSearchNoteData>(
        ResultSearchNoteData(1,"Nota 1"),
        ResultSearchNoteData(2, "Nota 2"),
        ResultSearchNoteData(3, "Nota 3")
    )

    private val _stateNote = MutableStateFlow<NoteHomeUiState>(NoteHomeUiState.Loading)
    val notes: StateFlow<NoteHomeUiState>
        get() = _stateNote

    private val _stateNoteResult = MutableStateFlow<NoteHomeResultUiState>(NoteHomeResultUiState.Empty)
    val stateNoteResult : StateFlow<NoteHomeResultUiState>
        get() = _stateNoteResult


    private val _taskFirebase = MutableLiveData<Task<Unit>>()
    val taskFirebase : LiveData<Task<Unit>>
        get() = _taskFirebase


    init {
        viewModelScope.launch {
            toListAllNoteUseCase.invoke().collect{it ->
                if(it.isEmpty()){
                    _stateNote.value = NoteHomeUiState.Empty
                }else{
                    _stateNote.value = NoteHomeUiState.Success(it)
                    _stateNoteResult.value = NoteHomeResultUiState.Success(
                        listResult
                    )
                }
            }

        }
    }

    fun onCloudNote(item: NoteItem){
        viewModelScope.launch{
            val result = insertNoteFirebase.invoke(item)
        }
    }

    fun onPin(id: Long, pin: Boolean){
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->

        }){

        }
    }



}