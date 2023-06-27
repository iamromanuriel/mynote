package com.roman.mynote.ui.note

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.data.database.entity.Note
import com.roman.mynote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    val _note = MutableLiveData<Note>()

    val liveDataColors = MutableLiveData<List<String>>()
    val liveDataDays = MutableLiveData<List<String>>()

    val mediatorLiveData = MediatorLiveData<List<String>>()

    val transformationDate = Transformations.map(liveDataDays){listColors ->
        //TODO: AFTER TO ASSIGNED LIVEDATA OPERATION
        val colors = mutableListOf<String>()
        colors.addAll(listColors)
        if(listColors.size == 5){
            colors.add("Domingo")
            colors.add("Sabado")
        }
        colors
    }

    init {
        mediatorLiveData.addSource(liveDataColors) { colors ->
            mediatorLiveData.value = colors
        }
        mediatorLiveData.addSource(transformationDate) { days ->
            mediatorLiveData.value = days.distinct()
        }

    }


    fun onStart(){
        viewModelScope.launch(Dispatchers.IO){
            liveDataColors.postValue(repository.getListNames())
            liveDataDays.postValue(repository.getDaysWeek())
        }
    }

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