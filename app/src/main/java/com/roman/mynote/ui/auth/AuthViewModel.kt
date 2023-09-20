package com.roman.mynote.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val _type = MutableLiveData<Boolean>()
    val liveDataType : LiveData<Boolean>
        get() = _type


    init {
        _type.postValue(false)
    }

    fun changeType(t: Boolean){
        _type.postValue(!t)
    }
}