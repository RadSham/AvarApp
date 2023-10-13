package com.example.avarapp.database

import com.example.avarapp.model.Word
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DictionaryDbManager {

    val db = Firebase.database.getReference(MAIN_NODE)

    fun getAllWords(readDataCallback: ReadDataCallback) {
        val query = db.orderByChild("/id")
        readDataFromDb(query, readDataCallback)
    }

    private fun readDataFromDb(query: Query, readDataCallback: ReadDataCallback) {
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val wordArray = ArrayList<Word>()
                for (item in snapshot.children) {
                    val word = item.getValue(Word::class.java)
                    if (word != null) {
                        wordArray.add(word)
                    }
                }
                readDataCallback.readData(wordArray)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    interface ReadDataCallback {
        fun readData(list: ArrayList<Word>)
    }

    companion object {
        const val MAIN_NODE = "main"
    }
}