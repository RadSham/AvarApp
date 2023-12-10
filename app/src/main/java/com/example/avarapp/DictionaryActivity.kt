package com.example.avarapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.avarapp.di.DaggerDictionaryActivityComponent
import com.example.avarapp.di.DictionaryActivityComponent
import com.example.avarapp.model.WordEntity
import com.example.avarapp.ui.BottomNavigationBar
import com.example.avarapp.ui.NavigationSetup
import com.example.avarapp.ui.theme.AvarAppTheme
import com.example.avarapp.ui.theme.RedMain
import com.example.avarapp.viewmodel.DictionaryViewModel
import com.example.avarapp.viewmodel.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import javax.inject.Inject

class DictionaryActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dictionaryActivityComponent: DictionaryActivityComponent
    private lateinit var selectedIndex: MutableState<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val applicationComponent = (application as App).applicationComponent
        dictionaryActivityComponent =
            DaggerDictionaryActivityComponent.factory().create(applicationComponent, this)
        dictionaryActivityComponent.inject(this)
        val dictionaryViewModel =
            ViewModelProvider(this, viewModelFactory)[DictionaryViewModel::class.java]

        setContent {
            AvarAppTheme {
                val dialog = remember { mutableStateOf(true) }

                val languages = listOf(
                    stringResource(id = R.string.avar_lang),
                    stringResource(id = R.string.rus_lang),
                    stringResource(id = R.string.eng_lang),
                    stringResource(id = R.string.tr_lang)
                )

                val expanded = remember { mutableStateOf(false) }
                selectedIndex = remember { mutableStateOf(0) }
                val wordsListState: MutableState<List<WordEntity>> =
                    remember { mutableStateOf(listOf()) }
                val query = remember { mutableStateOf("") }

                LaunchedEffect(key1 = 1) {
                    dictionaryViewModel.loadLocalWords(this@DictionaryActivity)
                    dictionaryViewModel.wStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                wordsListState.value = it.value
                                dialog.value = false
                                myLog(wordsListState.value.size)
                            }
                            is Result.Loading -> myLog(it)
                            is Result.Error -> myLog(it)
                        }
                    }
                }

                //StatusBar
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = RedMain,
                        darkIcons = false
                    )
                }
                //Navigation and Screens
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }) { padding ->
                    NavigationSetup(
                        navController = navController,
                        padding,
                        expanded,
                        selectedIndex,
                        languages,
                        query,
                        wordsListState,
                        dialog
                    )
                }
            }
        }
    }

    companion object {
        fun myLog(message: Any) {
            Log.d("MyLog", message.toString())
        }
    }
}