package my.exam.avarapp.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import my.exam.avarapp.model.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.TutorialEntity
import javax.inject.Inject

class DictionaryViewModel @Inject constructor() : ViewModel() {

    private val loadWordsManager = LoadWordsManager()
    private val loadPhrasesManager = LoadPhrasesManager()
    private val loadTutorialManager = LoadTutorialManager()
    private val _wStateFlow = MutableStateFlow<Result<List<WordEntity>>>(Result.Loading())
    private val _pStateFlow = MutableStateFlow<Result<List<CategoryPhraseEntity>>>(Result.Loading())
    private val _tStateFlow = MutableStateFlow<Result<List<TutorialEntity>>>(Result.Loading())
    val wStateFlow = _wStateFlow.asStateFlow()
    val pStateFlow = _pStateFlow.asStateFlow()
    val tStateFlow = _tStateFlow.asStateFlow()

    fun loadLocalWords(dictionaryActivity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {  loadWordsManager.getAllWords(dictionaryActivity) }.join()
            loadWordsManager.listenForWordFlow()
                .catch { _wStateFlow.value = Result.Error(it) }
                .collect { _wStateFlow.emit(Result.Success(it)) }
        }
    }

    fun loadLocalPhrases(dictionaryActivity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {  loadPhrasesManager.getAllPhrases(dictionaryActivity) }.join()
            loadPhrasesManager.listenForPhrasesFlow()
                .catch { _pStateFlow.value = Result.Error(it) }
                .collect { _pStateFlow.emit(Result.Success(it)) }
        }
    }

    fun loadLocalTutorial(dictionaryActivity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {  loadTutorialManager.getAllTutorial(dictionaryActivity) }.join()
            loadTutorialManager.listenForTutorialFlow()
                .catch { _tStateFlow.value = Result.Error(it) }
                .collect { _tStateFlow.emit(Result.Success(it)) }
        }
    }
}
