package com.roman.mynote.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.domain.usescase.SearchNoteUseCase
import com.romanuriel.domain.usescase.ToListAllNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _stateNoteResult = MutableStateFlow<NoteHomeResultUiState>(NoteHomeResultUiState.Empty)
    val result : StateFlow<NoteHomeResultUiState>
        get() = _stateNoteResult


    init {
        viewModelScope.launch {
            toListAllNoteUseCase.invoke().collect{it ->
                if(it.isEmpty()){
                    _stateNote.value = NoteHomeUiState.Empty
                }else{
                    _stateNote.value = NoteHomeUiState.Success(it)
                }
            }

        }
    }

}