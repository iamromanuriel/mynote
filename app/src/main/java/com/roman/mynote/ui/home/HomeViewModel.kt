package com.roman.mynote.ui.home

import androidx.lifecycle.ViewModel
import com.roman.mynote.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    val allNote = repository.allNote
}