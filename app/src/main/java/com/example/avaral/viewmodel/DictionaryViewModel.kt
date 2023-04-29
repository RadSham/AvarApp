package com.example.avaral.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avaral.database.DictionaryDb
import com.example.avaral.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DictionaryViewModel : ViewModel() {

    lateinit var db: DictionaryDb
    val flowWordsList = MutableStateFlow<List<Word>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db = DictionaryDb()
        }
    }

    fun loadAllWords() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                flowWordsList.value = db.getAllWords()
                println("MainViewModel.loadAllWords")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadEnWord(searchedEnword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                flowWordsList.value = db.getEnWord(searchedEnword)
                println("MainViewModel.loadEnWord")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}