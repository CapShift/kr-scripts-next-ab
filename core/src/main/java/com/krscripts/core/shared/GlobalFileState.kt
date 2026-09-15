package com.krscripts.app.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object GlobalFileState {
    private val _currentSelectedPath = MutableLiveData<String>("")
    val currentSelectedPath: LiveData<String> = _currentSelectedPath

    private val _selectedPathList = MutableLiveData<List<String>>(emptyList())
    val selectedPathList: LiveData<List<String>> = _selectedPathList

    fun updateSelectedPath(path: String?) {
        val realPath = path ?: ""
        _currentSelectedPath.postValue(realPath)
        if (realPath.isNotEmpty()) {
            _selectedPathList.postValue(listOf(realPath))
        } else {
            _selectedPathList.postValue(emptyList())
        }
    }

    fun clear() {
        _currentSelectedPath.postValue("")
        _selectedPathList.postValue(emptyList())
    }
}
