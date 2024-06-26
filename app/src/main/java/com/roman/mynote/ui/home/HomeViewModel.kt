package com.roman.mynote.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.utils.stateflow.NoteHomeResultUiState
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.domain.usescase.DeleteNoteUseCase
import com.romanuriel.domain.usescase.PinNoteUseCase
import com.romanuriel.domain.usescase.SearchNoteUseCase
import com.romanuriel.domain.usescase.ToListAllNoteUseCase
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
    private val repositoryNote: NoteRepository,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val pinUsesCase: PinNoteUseCase
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

    private fun onListNote(){
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
            /*searchUseCase.invoke(text).collect{
                _stateNoteResult.value = NoteHomeResultUiState.Success(it)
            }*/
        }
    }

    fun onSelectNote(noteItem: NoteItem){
        repositoryNote.setNoteItem(noteItem)
    }

    fun onDeleteNoteItem(noteItem: NoteItem){
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->

        }){
            deleteNoteUseCase.invoke(noteItem)
        }
    }

    fun onPin(noteItem: NoteItem){
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->

        }){

        }
    }
}