package com.roman.mynote.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUseCase: SearchNoteUseCase,
    private val toListAllNoteUseCase: ToListAllNoteUseCase
): ViewModel() {

    private val _stateNote = MutableStateFlow<NoteHomeUiState>(NoteHomeUiState.Loading)
    val notes: StateFlow<NoteHomeUiState>
        get() = _stateNote

    private val _stateNoteResult = MutableStateFlow<NoteHomeResultUiState>(NoteHomeResultUiState.Loading)
    val stateNoteResult : StateFlow<NoteHomeResultUiState>
        get() = _stateNoteResult




    init {
        onListNote()
    }

    fun onListNote(){
        viewModelScope.launch (CoroutineExceptionHandler { _, throwable ->
            _stateNote.value = NoteHomeUiState.Error(throwable.localizedMessage?:"")
        }){
            toListAllNoteUseCase.invoke().collect{
                if(it.isEmpty()){
                    _stateNote.value = NoteHomeUiState.Empty
                }else{
                    _stateNote.value = NoteHomeUiState.Success(it)
                }
            }
        }
    }

    fun onSearch(text:String){
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _stateNoteResult.value = NoteHomeResultUiState.Error(throwable.localizedMessage?:"")
        }){
            searchUseCase.invoke(text).collect{
                _stateNoteResult.value = NoteHomeResultUiState.Success(it)
            }
        }
    }





}