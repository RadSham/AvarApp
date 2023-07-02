package com.example.avarapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avarapp.activity.DictionaryActivity
import com.example.avarapp.database.DictionaryDbManager
import com.example.avarapp.dialog.ProgressDialog
import com.example.avarapp.model.Word

class DictionaryViewModel : ViewModel() {
    private val dictionaryDbManager = DictionaryDbManager()
    val liveWordsData = MutableLiveData<ArrayList<Word>>()

    fun loadAllWords(dictionaryActivity: DictionaryActivity) {
        val dialog = ProgressDialog.createProgressDialog(dictionaryActivity)
        dictionaryDbManager.getAllWords(object : DictionaryDbManager.ReadDataCallback {
            override fun readData(list: ArrayList<Word>) {
                liveWordsData.value = list
                dialog.dismiss()
            }
        })
    }
}

/*
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
}*/
