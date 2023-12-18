package com.example.avarapp.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.avarapp.R
import com.example.avarapp.model.WordEntity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import java.io.BufferedReader
import java.lang.reflect.Type


class LoadWordsManager {
    private var tempList = mutableListOf<WordEntity>()
    private val refreshIntervalMs: Long = 2000

    fun startLoad(
        dictionaryActivity: Activity
    ) {
        getAllWords(dictionaryActivity)
    }

    //Filling database with the data from JSON
    private fun getAllWords(context: Context) {
        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val inputStream = context.resources.openRawResource(R.raw.words)
            //using Buffered reader to read the inputstream byte
            val br = BufferedReader(inputStream.reader())
            //create type of List<WordEntity>
            val type: Type = object : TypeToken<List<WordEntity?>?>() {}.type
            tempList = GsonBuilder().create().fromJson(br, type)
        }
        //error when exception occurs
        catch (e: JSONException) {
            Log.d("MyLog", "fillWithStartingWords: $e")
        }
    }

    fun listenForWordFlow(): Flow<List<WordEntity>> = flow {
        emit(tempList) // Emits the result of the request to the flow
        delay(refreshIntervalMs) // Suspends the coroutine for some time
    }
}