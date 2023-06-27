package com.roman.mynote.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.data.repository.HomeRepository
import com.roman.mynote.utils.resource.IResourceProvider
import com.roman.mynote.utils.resource.ResourceProvider
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val resourceProvider: IResourceProvider
): ViewModel() {
    val stateNote = MutableStateFlow<NoteHomeUiState>(NoteHomeUiState.Loading)

    fun onStart(){
        viewModelScope.launch {
            repository.getListNote().collect{
                stateNote.value = NoteHomeUiState.Success(it)
            }
        }
    }

    fun updatePin(note: Note){
        viewModelScope.launch {
            repository.updateForPin(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}