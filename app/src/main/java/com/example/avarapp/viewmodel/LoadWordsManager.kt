package com.example.avarapp.viewmodel

import android.content.Context
import android.util.Log
import com.example.avarapp.R
import com.example.avarapp.DictionaryActivity
import com.example.avarapp.model.WordEntity
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader


class LoadWordsManager {

    fun startLoad(
        dictionaryActivity: DictionaryActivity,
        readDataCallback: ReadDataCallback
    ) {
        getAllWords(dictionaryActivity, readDataCallback)
    }

    //Filling database with the data from JSON
    private fun getAllWords(context: Context, readDataCallback: ReadDataCallback) {
        val tempList = mutableListOf<WordEntity>()

        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val words = loadJSONArray(context)
            //looping through the variable as specified fields are loaded with data
            for (i in 0 until words.length()) {
                //variable to obtain the JSON object
                val item = words.getJSONObject(i)
                //Using the JSON object to assign data
                val wordId = item.getInt("id")
                val wordAvname = item.getString("avname")
                val wordAvderivatives = item.getString("avderivatives")
                val wordAvcat = item.getString("avcat")
                val wordRusname = item.getString("rusname")
                val wordTrname = item.getString("trname")
                val wordEnname = item.getString("enname")
                val wordAvexample = item.getString("avexample")

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
            readDataCallback.readData(tempList)
            Log.d("MyLog", "tempList size ${tempList.size}")
        }
        //error when exception occurs
        catch (e: JSONException) {
            Log.d("MyLog", "fillWithStartingWords: $e")
        }
    }

    // loads JSON data
    private fun loadJSONArray(context: Context): JSONArray {
        //obtain input byte
        val inputStream = context.resources.openRawResource(R.raw.words)
        //using Buffered reader to read the inputstream byte
        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

    interface ReadDataCallback {
        fun readData(list: List<WordEntity>)
    }
}