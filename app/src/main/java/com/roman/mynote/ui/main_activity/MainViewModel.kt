package com.roman.mynote.ui.main_activity


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romanuriel.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(mainRepository: MainRepository): ViewModel() {

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        }){
            mainRepository.initDatabase()
        }
    }
}