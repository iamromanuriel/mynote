package com.roman.mynote.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.mynote.data.repository.NoteRepository
import com.roman.mynote.data.database.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    val listName = MutableLiveData<List<String>>()
    val listDays = MutableLiveData<List<String>>()


    fun onStart(){
        viewModelScope.launch(Dispatchers.IO){
            listName.postValue(repository.getListNames())
            listDays.postValue(repository.getDaysWeek())
        }
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO){

    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }



}