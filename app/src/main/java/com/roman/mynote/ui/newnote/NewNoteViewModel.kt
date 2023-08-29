package com.roman.mynote.ui.newnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import com.romanuriel.utils.TypeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(private val insertNewNoteUseCase: InsertNewNoteUseCase) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    private val ioThread = CoroutineScope(Dispatchers.IO)


    fun newNote(title: String, note: String) {
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _message.postValue(throwable.localizedMessage)
        }) {
            insertNewNoteUseCase.invoke(title, note, TypeCategory.NOTE)
        }
    }
}