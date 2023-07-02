package com.example.avarapp.database

import com.example.avarapp.model.Word
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.ResultSet


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

    private fun makeWord(result: ResultSet): Word {
        val id = result.getInt("id")
        val avname = result.getString("avname")
        val avderivatives = result.getString("avderivatives")
        val avcat = result.getString("avcat")
        val rusname = result.getString("rusname")
        val trname = result.getString("trname")
        val enname = result.getString("enname")
        val avexample = result.getString("avexample")
        return Word(id, avname, avderivatives, avcat, rusname, trname, enname, avexample)
    }

    interface ReadDataCallback {
        fun readData(list: ArrayList<Word>)
    }

    companion object {
        const val MAIN_NODE = "main"
        const val ID_NODE = "id"
    }
}

/* private var connection: Connection? = null

 private val host = "10.0.2.2"
 private val database = "postgres"
 private val port = 5432
 private val user = "postgres"
 private val pass = "123456"
 private var url = "jdbc:postgresql://%s:%d/%s"
 private var status = false

 init {
     url = String.format(url, host, port, database)
     connect()
 }

 private fun connect() {
     try {
         Class.forName("org.postgresql.Driver")
         connection = DriverManager.getConnection(url, user, pass);
     } catch (e: ClassNotFoundException) {
         e.printStackTrace()
     }

 }

fun getAllWords(): List<Word> {
    val query = connection?.prepareStatement("SELECT * FROM word")
    // the query is executed and results are fetched
    val result = query?.executeQuery()
    // an empty list for holding the results
    val wordsList = mutableListOf<Word>()
    if (result != null) {
        while (result.next()) {
            val tempWord = makeWord(result)
            wordsList.add(tempWord)
        }
    } else {
        Log.d("MyLog", "Result == null")
    }
    return wordsList
}

fun getEnWord(searchedEnword: String): List<Word> {
    val query =
        connection?.prepareStatement("SELECT * FROM word WHERE enname = '$searchedEnword' ORDER BY id")
    val result = query?.executeQuery()
    val flowWordsList = MutableStateFlow<List<Word>>(emptyList())
    val wordsList = mutableListOf<Word>()

    if (result != null) {
        while (result.next()) {
            val tempWord = makeWord(result)
            wordsList.add(tempWord)
        }
    } else {
        Log.d("MyLog", "result == null")
    }
    println("MainDb.getEnWord")
    return wordsList
} */


