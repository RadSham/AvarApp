package my.exam.avarapp.viewmodel

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.exam.avarapp.R
import my.exam.avarapp.model.CategoryPhraseEntity
import java.io.BufferedReader
import java.lang.reflect.Type

class LoadPhrasesManager {
    private var tempList = mutableListOf<CategoryPhraseEntity>()

    //Filling list with the data from JSON
    fun getAllPhrases(context: Context) {
        var br: BufferedReader? = null
        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val inputStream = context.resources.openRawResource(R.raw.phrases)
            //using Buffered reader to read the inputstream byte
            br = BufferedReader(inputStream.reader())
            //create type of List<CategoryPhraseEntity>
            val type: Type = object : TypeToken<List<CategoryPhraseEntity?>?>() {}.type
            tempList.addAll(GsonBuilder().create().fromJson(br, type))
        }
        //error when exception occurs
        catch (e: Exception) {
            Log.d("MyLog", "getAllPhrases exception: $e")
        } finally {
            br?.close()
        }
    }

    fun listenForPhrasesFlow(): Flow<List<CategoryPhraseEntity>> = flow {
        emit(tempList) // Emits the result of the request to the flow
    }
}