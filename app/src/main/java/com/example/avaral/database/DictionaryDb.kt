package com.example.avaral.database

import android.util.Log
import com.example.avaral.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class DictionaryDb {
    private var connection: Connection? = null

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

    fun connect() {
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
}


