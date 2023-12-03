package com.example.avarapp.viewmodel

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avarapp.model.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor() : ViewModel() {

    private val loadWordsManager = LoadWordsManager()
    private var _liveWordsData =  MutableLiveData<List<WordEntity>>()
    val liveWordsData: LiveData<List<WordEntity>>
        get() = _liveWordsData

    fun loadLocalWords(
        dictionaryActivity: Activity,
        dialog: MutableState<Boolean>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var tempList = listOf<WordEntity>()
            loadWordsManager.startLoad(
                dictionaryActivity,
                object : LoadWordsManager.ReadDataCallback {
                    override fun readData(list: List<WordEntity>) {
                        tempList = list
                        dialog.value = false
                    }
                })
            launch(Dispatchers.Main) {
                _liveWordsData.value = tempList
            }
        }
    }
}
