package my.exam.avarapp.viewmodel

import android.content.Context
import android.util.Log
import my.exam.avarapp.R
import my.exam.avarapp.model.WordEntity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.lang.reflect.Type


class LoadWordsManager {
    private var tempList = mutableListOf<WordEntity>()

    //Filling list with the data from JSON
    fun getAllWords(context: Context) {
        var br: BufferedReader? = null
        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val inputStream = context.resources.openRawResource(R.raw.words)
            //using Buffered reader to read the inputstream byte
            br = BufferedReader(inputStream.reader())
            //create type of List<WordEntity>
            val type: Type = object : TypeToken<List<WordEntity?>?>() {}.type
            tempList.addAll(GsonBuilder().create().fromJson(br, type))
        }
        //error when exception occurs
        catch (e: Exception) {
            e.printStackTrace()
        } finally {
            br?.close()
        }
    }

    fun listenForWordFlow(): Flow<List<WordEntity>> = flow {
        emit(tempList) // Emits the result of the request to the flow
    }
}