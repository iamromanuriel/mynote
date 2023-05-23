package com.roman.mynote.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.data.model.Note
import com.roman.mynote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    val _note = MutableLiveData<Note>()


    fun showNote(id: Int){
        viewModelScope.launch {
            val resultNoteById = repository.getNoteById(id)
            _note.postValue(resultNoteById)
        }
    }

    fun update(title: String, note: String){
        viewModelScope.launch {
            repository.update(_note.value!!.id,title, note)
        }
    }

    fun delete(){
        viewModelScope.launch {
            val referenceNote = _note.value!!
            repository.delete(referenceNote)
        }
    }

}