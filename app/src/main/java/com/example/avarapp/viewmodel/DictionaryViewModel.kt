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
