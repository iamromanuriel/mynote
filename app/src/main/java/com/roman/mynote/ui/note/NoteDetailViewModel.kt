package com.roman.mynote.ui.note

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(): ViewModel() {


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

}