package com.hcaula.smashpe.ui.matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SectionsViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }
}