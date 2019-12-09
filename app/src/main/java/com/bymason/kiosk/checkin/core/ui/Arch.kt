package com.bymason.kiosk.checkin.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StateHolder<T : Any>(state: T) {
    private val _liveData = MutableLiveData(state)
    val liveData: LiveData<T> get() = _liveData

    var value: T = state
        set(value) {
            field = value
            _liveData.postValue(value)
        }
}
