package com.example.avarapp

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
import androidx.compose.runtime.SideEffect
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
import com.example.avarapp.ui.theme.RedMain
import com.example.avarapp.viewmodel.DictionaryViewModel
import com.example.avarapp.viewmodel.MyDictionaryViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class DictionaryActivity : ComponentActivity() {
    private lateinit var dictionaryViewModel: DictionaryViewModel

    private lateinit var selectedIndex: MutableState<Int>

    private val languages = listOf("Авар мацI", "Русский язык", "English", "Turkce")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myViewModelFactory = MyDictionaryViewModelFactory()

        dictionaryViewModel =
            ViewModelProvider(this, myViewModelFactory)[DictionaryViewModel::class.java]
        setContent {
            AvarAppTheme {
                val expanded = remember { mutableStateOf(false) }
                selectedIndex = remember { mutableStateOf(0) }

                //wordsLazyColumn
                val wordsListState: MutableState<List<WordEntity>> =
                    remember { mutableStateOf(listOf()) }
                val query = remember { mutableStateOf("") }

                dictionaryViewModel.loadLocalWords(this@DictionaryActivity)

                dictionaryViewModel.liveWordsData.observe(this@DictionaryActivity) {
                    wordsListState.value = it
                    Log.d("MyLog", "wordsListState.value size ${wordsListState.value.size}")
                    Log.d("MyLog", "it size ${it.size}")
                }
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = RedMain,
                        darkIcons = false
                    )
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(this, "Dictionary")
                    }) { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        LanguageChooser(
                            expanded,
                            selectedIndex,
                            languages
                        )
                        SearchView(query)

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