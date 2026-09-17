package com.krscripts.app.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

object GlobalFileState {
    // 最新单个选中路径（文件/文件夹）
    private val _currentSelectedPath = MutableLiveData<String>("")
    val currentSelectedPath: LiveData<String> = _currentSelectedPath

    // 历史，若未来支持多选可以改成List<String>，当前项目为单选
    private val _selectedPathList = MutableLiveData<List<String>>(emptyList())
    val selectedPathList: LiveData<List<String>> = _selectedPathList

    // 更新，所有文件选择统一调用这个方法
    fun updateSelectedPath(path: String?) {
        val realPath = path ?: ""
        _currentSelectedPath.postValue(realPath)
        if(realPath.isNotEmpty()){
            _selectedPathList.postValue(listOf(realPath))
        }else{
            _selectedPathList.postValue(emptyList())
        }
    }

    // 清空
    fun clear(){
        _currentSelectedPath.postValue("")
        _selectedPathList.postValue(emptyList())
    }
}
