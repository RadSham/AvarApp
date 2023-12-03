package com.example.avarapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.avarapp.model.WordEntity

@Composable
fun DictionaryScreen(
    padding: PaddingValues,
    expanded: MutableState<Boolean>,
    selectedIndex: MutableState<Int>,
    languages: List<String>,
    query: MutableState<String>,
    wordsListState: MutableState<List<WordEntity>>
) {
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