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
    private val tempList = mutableListOf<WordEntity>()
    private val refreshIntervalMs: Long = 2000

    fun startLoad(
        dictionaryActivity: Activity
    ) {
        getAllWords(dictionaryActivity)
    }

    fun listenForWordFlow(): Flow<List<WordEntity>> = flow {
        while (true) {
            emit(tempList) // Emits the result of the request to the flow
            delay(refreshIntervalMs) // Suspends the coroutine for some time
        }
    }

    //Filling database with the data from JSON
    private fun getAllWords(context: Context) {
        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val words = loadListOfWordEntities(context)
            //looping through the variable as specified fields are loaded with data
            for (i in words) {
                //variable to obtain the JSON object
                //Using the JSON object to assign data
                val wordId = i.id
                val wordAvname = i.avname
                val wordAvderivatives = i.avderivatives
                val wordAvcat = i.avcat
                val wordRusname = i.rusname
                val wordTrname = i.trname
                val wordEnname = i.enname
                val wordAvexample = i.avexample

                //data loaded to the entity
                val wordEntity = WordEntity(
                    wordId,
                    wordAvname,
                    wordAvderivatives,
                    wordAvcat,
                    wordRusname,
                    wordTrname,
                    wordEnname,
                    wordAvexample
                )
                //using dao to insert data to the database
                tempList.add(wordEntity)

            }
//            readDataCallback.readData(tempList)
            Log.d("MyLog", "tempList size ${tempList.size}")
        }
        //error when exception occurs
        catch (e: JSONException) {
            Log.d("MyLog", "fillWithStartingWords: $e")
        }

    }

    // loads JSON data
    private fun loadListOfWordEntities(context: Context): List<WordEntity> {
        //obtain input byte
        val inputStream = context.resources.openRawResource(R.raw.words)
        //using Buffered reader to read the inputstream byte
        val br = BufferedReader(inputStream.reader())
        val type: Type = object : TypeToken<List<WordEntity?>?>() {}.type
        return GsonBuilder().create().fromJson(br, type)

    }

    interface ReadDataCallback {
        fun readData(list: List<WordEntity>)
    }
}