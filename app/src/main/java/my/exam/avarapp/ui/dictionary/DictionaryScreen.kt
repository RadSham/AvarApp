package my.exam.avarapp.ui.dictionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import my.exam.avarapp.R
import my.exam.avarapp.model.WordEntity

@Composable
fun DictionaryScreen(
    padding: PaddingValues,
    wordsListState: MutableState<List<WordEntity>>
) {
    val query = remember { mutableStateOf("") }
    val languages = listOf(
        stringResource(id = R.string.avar_lang),
        stringResource(id = R.string.rus_lang),
        stringResource(id = R.string.eng_lang),
        stringResource(id = R.string.tr_lang)
    )
    val selectedIndexFirst = remember { mutableIntStateOf(0) }
    val selectedIndexSecond = remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.padding(padding)) {
        LanguageChooser(
            selectedIndexFirst, selectedIndexSecond,languages
        )
        SearchView(query)
        WordsList(query, wordsListState, languages[selectedIndexFirst.intValue], languages[selectedIndexSecond.intValue])
    }
}