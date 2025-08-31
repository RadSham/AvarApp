package my.exam.avarapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.TutorialEntity
import my.exam.avarapp.model.WordEntity
import my.exam.avarapp.navigation.NavigationSetup
import my.exam.avarapp.ui.activity.BottomNavigationBar
import my.exam.avarapp.ui.activity.ProcessBar
import my.exam.avarapp.ui.theme.AvarAppTheme
import my.exam.avarapp.ui.theme.RedMain
import my.exam.avarapp.viewmodel.DictionaryViewModel
import my.exam.avarapp.util.Result

@AndroidEntryPoint
class DictionaryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvarAppTheme {
                val progressBarLoading = remember { mutableStateOf(true) }
                val wordsListState: MutableState<List<WordEntity>> =
                    remember { mutableStateOf(listOf()) }
                val phrasesListState: MutableState<List<CategoryPhraseEntity>> =
                    remember { mutableStateOf(listOf()) }
                val tutorialListState: MutableState<List<TutorialEntity>> =
                    remember { mutableStateOf(listOf()) }
                val dictionaryViewModel: DictionaryViewModel = hiltViewModel()
                LaunchedEffect(key1 = 1) {
                    dictionaryViewModel.loadLocalWords(this@DictionaryActivity)
                    dictionaryViewModel.wStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                wordsListState.value = it.value
                                progressBarLoading.value = false
                            }

                            is Result.Loading -> progressBarLoading.value = true
                            is Result.Error -> it.error.printStackTrace()
                        }
                    }
                }

                LaunchedEffect(key1 = 2) {
                    dictionaryViewModel.loadLocalPhrases(this@DictionaryActivity)
                    dictionaryViewModel.pStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                phrasesListState.value = it.value
//                                progressBarLoading.value = false
                            }

                            is Result.Loading -> Log.d("MyLog", it.toString())
                            //progressBarLoading.value = true
                            is Result.Error -> it.error.printStackTrace()
                        }
                    }
                }

                LaunchedEffect(key1 = 3) {
                    dictionaryViewModel.loadLocalTutorial(this@DictionaryActivity)
                    dictionaryViewModel.tStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                tutorialListState.value = it.value
//                                progressBarLoading.value = false
                            }

                            is Result.Loading -> Log.d("MyLog", it.toString())
                            //progressBarLoading.value = true
                            is Result.Error -> it.error.printStackTrace()
                        }
                    }
                }

                //StatusBar
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = RedMain, darkIcons = false
                    )
                }
                //Navigation and Screens
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.navigationBarsPadding(),
                    contentWindowInsets = WindowInsets.systemBars,
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }) { padding ->
                    ProcessBar(progressBarLoading)
                    NavigationSetup(
                        navController,
                        padding,
                        wordsListState,
                        phrasesListState,
                        tutorialListState,
                        object : ShowToast {
                            override fun show(message: String) {
                                Toast.makeText(this@DictionaryActivity, message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    )
                }
            }
        }
    }
}

interface ShowToast {
    fun show(message: String)
}