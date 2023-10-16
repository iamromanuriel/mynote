package com.roman.mynote.ui.alert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.mynote.utils.model.ItemNewModel
import com.romanuriel.utils.enums.TypeNotice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(): ViewModel() {
    private val _dataItemNews = MutableStateFlow<List<ItemNewModel>>(emptyList())
    val dataItemNew : StateFlow<List<ItemNewModel>>
        get() = _dataItemNews


    init {
        _dataItemNews.value = list()
    }

    private fun list() = listOf(
        ItemNewModel(1,TypeNotice.ALERT,"Alerta","xd"),
        ItemNewModel(2,TypeNotice.NEW_FEATURES,"Anuncio","fe ea"),
        ItemNewModel(3,TypeNotice.ALERT,"Alerta 23","fa re bg"),
        ItemNewModel(3,TypeNotice.ALERT,"Alerta 736198","pobche  juias"),
        ItemNewModel(4,TypeNotice.REMINDER,"Rordatorio","hnal oahed lin")
    )


}