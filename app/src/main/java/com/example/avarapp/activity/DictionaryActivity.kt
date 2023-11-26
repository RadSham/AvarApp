package com.example.avarapp.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.avarapp.model.WordEntity
import com.example.avarapp.ui.BottomNavigationBar
import com.example.avarapp.ui.LanguageChooser
import com.example.avarapp.ui.SearchView
import com.example.avarapp.ui.WordsList
import com.example.avarapp.ui.theme.AvarAppTheme
import com.example.avarapp.viewmodel.DictionaryViewModel
import com.example.avarapp.viewmodel.MyDictionaryViewModelFactory

class DictionaryActivity : ComponentActivity() {
    private lateinit var dictionaryViewModel: DictionaryViewModel

    private lateinit var selectedIndex: MutableState<Int>

    private val languages = listOf("Авар мацI", "Русский язык", "English", "Turkce")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModelFactory = MyDictionaryViewModelFactory()

        dictionaryViewModel = ViewModelProvider(this, myViewModelFactory)[DictionaryViewModel::class.java]

        dictionaryViewModel.loadLocalWords(this)

        setContent {
            AvarAppTheme {
                val expanded = remember { mutableStateOf(false) }
                val expanded2 = remember { mutableStateOf(false) }
                selectedIndex = remember { mutableStateOf(0) }
                val selectedIndex2 = remember { mutableStateOf(1) }

                //wordsLazyColumn
                val wordsListState: MutableState<List<WordEntity>> =
                    remember { mutableStateOf(listOf()) }
                val query = remember { mutableStateOf("") }

                dictionaryViewModel.liveWordsData.observe(this@DictionaryActivity) {
                    wordsListState.value = it
                    Log.d("MyLog", "wordsListState.value size ${wordsListState.value.size}")
                    Log.d("MyLog", "it size ${it.size}")
                }

                Scaffold(topBar = {
                    Column {
                        LanguageChooser(
                            expanded,
                            expanded2,
                            selectedIndex,
                            selectedIndex2,
                            languages
                        )
                        SearchView(query)
                    }
                },
                    bottomBar = {
                        BottomNavigationBar(this, "Dictionary")
                    }) { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        WordsList(wordsListState, query, languages[selectedIndex.value])
                    }
                }
            }
        }
    }


    companion object {
        fun myLog(message: Any) {
            Log.d("MyLog", message.toString())
        }

        fun showToast(
            context: Context,
            message: String,
            duration: Int = Toast.LENGTH_LONG
        ) {
            Toast.makeText(context, message, duration).show()
        }
    }
}