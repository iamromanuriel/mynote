package com.roman.mynote.ui.newnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.mynote.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _message = MutableLiveData<String>()
    private val ioThread = CoroutineScope(Dispatchers.IO)


    fun newNote(title: String, note: String) {
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _message.postValue(throwable.localizedMessage)
        }) {
        }
    }
}