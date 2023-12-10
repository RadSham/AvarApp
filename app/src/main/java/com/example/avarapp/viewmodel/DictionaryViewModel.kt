package com.example.avarapp.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avarapp.model.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor() : ViewModel() {

    private val loadWordsManager = LoadWordsManager()

    private val _wStateFlow = MutableStateFlow<Result<List<WordEntity>>>(Result.Loading())
    val wStateFlow = _wStateFlow.asStateFlow()

    fun loadLocalWords(
        dictionaryActivity: Activity
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loadWordsManager.startLoad(
                dictionaryActivity
            )

            loadWordsManager.listenForWordFlow()
                .catch { _wStateFlow.value = Result.Error(it) }
                .collect {
                    _wStateFlow.emit(Result.Success(it))
                }
        }
    }
}
