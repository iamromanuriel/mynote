package com.roman.mynote.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanuriel.core.Task
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import com.romanuriel.utils.TypeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val insertNewNoteUseCase: InsertNewNoteUseCase ): ViewModel() {
    private val _task = MutableLiveData<Task<Unit>>()
    private val ioThread = CoroutineScope(Dispatchers.IO)
    val task : LiveData<Task<Unit>>
        get() = _task
    fun onCreateReminder(date: Long, title: String) {
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _task.postValue(Task.Error(Exception(throwable.localizedMessage)))
        }) {
            val result = insertNewNoteUseCase.invoke(
                title = title,
                dateCreate = date,
                type = TypeCategory.REMINDER
            )
            _task.postValue(
                result
            )
        }

    }
}