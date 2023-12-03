package com.example.avarapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avarapp.DictionaryActivity
import com.example.avarapp.model.WordEntity
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(): ViewModel() {

    private val loadWordsManager = LoadWordsManager()
    val liveWordsData = MutableLiveData<List<WordEntity>>()

    fun loadLocalWords(dictionaryActivity: DictionaryActivity) {
//        val dialog = ProgressDialog.createProgressDialog(dictionaryActivity)
        loadWordsManager.startLoad(
            dictionaryActivity,
            object : LoadWordsManager.ReadDataCallback {
                override fun readData(list: List<WordEntity>) {
                    liveWordsData.value = list
                    Log.d("MyLog", "liveWordsData.value ${liveWordsData.value?.size}")
//                    dialog.dismiss()
                }
            })

    }
}
