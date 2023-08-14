package com.roman.mynote.ui.home

import androidx.lifecycle.ViewModel
import com.roman.mynote.data.repository.HomeRepository
import com.roman.mynote.utils.resource.IResourceProvider
import com.roman.mynote.utils.stateflow.NoteHomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val resourceProvider: IResourceProvider
): ViewModel() {
    val stateNote = MutableStateFlow<NoteHomeUiState>(NoteHomeUiState.Loading)




}