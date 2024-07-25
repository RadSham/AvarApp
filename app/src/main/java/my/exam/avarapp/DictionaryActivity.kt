package my.exam.avarapp

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import my.exam.avarapp.di.DaggerDictionaryActivityComponent
import my.exam.avarapp.di.DictionaryActivityComponent
import my.exam.avarapp.model.WordEntity
import my.exam.avarapp.ui.activity.BottomNavigationBar
import my.exam.avarapp.ui.activity.ProcessBar
import my.exam.avarapp.ui.theme.AvarAppTheme
import my.exam.avarapp.ui.theme.RedMain
import my.exam.avarapp.viewmodel.DictionaryViewModel
import my.exam.avarapp.viewmodel.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.TutorialEntity
import my.exam.avarapp.navigation.NavigationSetup
import javax.inject.Inject

class DictionaryActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dictionaryActivityComponent: DictionaryActivityComponent
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
                val progressBarLoading = remember { mutableStateOf(true) }
                val wordsListState: MutableState<List<WordEntity>> =
                    remember { mutableStateOf(listOf()) }
                val phrasesListState: MutableState<List<CategoryPhraseEntity>> =
                    remember { mutableStateOf(listOf()) }
                val tutorialListState: MutableState<List<TutorialEntity>> =
                    remember { mutableStateOf(listOf()) }
                LaunchedEffect(key1 = 1) {
                    dictionaryViewModel.loadLocalWords(this@DictionaryActivity)
                    dictionaryViewModel.wStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                wordsListState.value = it.value
                                progressBarLoading.value = false
                            }
                            is Result.Loading -> progressBarLoading.value = true
                            is Result.Error -> Log.d("MyLog", it.toString())
                        }
                    }
                }

                LaunchedEffect(key1 = 2){
                    dictionaryViewModel.loadLocalPhrases(this@DictionaryActivity)
                    dictionaryViewModel.pStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                phrasesListState.value = it.value
//                                progressBarLoading.value = false
                            }
                            is Result.Loading -> Log.d("MyLog", it.toString()) //progressBarLoading.value = true
                            is Result.Error -> Log.d("MyLog", it.toString())
                        }
                    }
                }

                LaunchedEffect(key1 = 3){
                    dictionaryViewModel.loadLocalTutorial(this@DictionaryActivity)
                    dictionaryViewModel.tStateFlow.collect {
                        when (it) {
                            is Result.Success -> {
                                tutorialListState.value = it.value
//                                progressBarLoading.value = false
                            }
                            is Result.Loading -> Log.d("MyLog", it.toString()) //progressBarLoading.value = true
                            is Result.Error -> Log.d("MyLog", it.toString())
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
                    bottomBar = {
                    BottomNavigationBar(navController)
                }) { padding ->
                    ProcessBar(progressBarLoading)
                    NavigationSetup(
                        navController = navController, padding, wordsListState, phrasesListState, tutorialListState
                    )
                }
            }
        }
    }
}