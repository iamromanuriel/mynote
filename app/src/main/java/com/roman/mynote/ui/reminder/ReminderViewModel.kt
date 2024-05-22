package com.roman.mynote.ui.reminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.mynote.utils.model.DayReminder
import com.roman.mynote.utils.model.ReminderModel
import com.roman.mynote.utils.model.TimeReminder
import com.romanuriel.core.Task
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import com.romanuriel.utils.TypeCategory
import com.romanuriel.utils.enums.StateNewReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val insertNewNoteUseCase: InsertNewNoteUseCase ): ViewModel() {
    private val ioThread = CoroutineScope(Dispatchers.IO)
    private val _task = MutableLiveData<Task<Unit>>()
    private var reminderModel = ReminderModel()
    val task : LiveData<Task<Unit>>
        get() = _task
    private val _stateProgress = MutableLiveData<StateNewReminder>()
    val stateProgress : LiveData<StateNewReminder>
        get() = _stateProgress
    init {
        _stateProgress.postValue(reminderModel.getState())
    }

    fun onCreateReminder(title: String = "") {
        ioThread.launch(CoroutineExceptionHandler { _, throwable ->
            _task.postValue(Task.Error(Exception(throwable.message)))
        }) {
            if(title.isNotEmpty()){
                val result = insertNewNoteUseCase.invoke(
                    title = title,
                    dateCreate = reminderModel.getDate().time,
                    type = TypeCategory.REMINDER
                )
                _task.postValue( result )
            }else{
                _task.postValue(
                    Task.Error(Exception("Revise los datos"))
                )
            }
        }
    }
    fun setDay(year: Int, month: Int, dayOfMonth: Int){
        reminderModel.day = DayReminder(dayOfMonth, month, year)
        _stateProgress.postValue(reminderModel.getState())
    }
    fun setHora(hour: Int, min: Int){
        reminderModel.time = TimeReminder(hour, min)
        _stateProgress.postValue(reminderModel.getState())
    }
}