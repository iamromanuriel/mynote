package com.roman.mynote.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(): ViewModel() {
    val listName = MutableLiveData<List<String>>()
    val listDays = MutableLiveData<List<String>>()



}