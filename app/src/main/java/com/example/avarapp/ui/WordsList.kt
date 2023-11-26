package com.example.avarapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.avarapp.activity.DictionaryActivity.Companion.myLog
import com.example.avarapp.model.WordEntity
import java.util.Locale

@Composable
fun WordsList(
    wordsState: MutableState<List<WordEntity>>,
    query: MutableState<String>,
    language: String
) {
    myLog("HERE WordsList")
    val filteredList = wordsState.value
        .filter {
            when (language) {
                "Авар мацI" -> it.avname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                "Русский язык" -> it.rusname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                "English" -> it.enname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                "Turkce" -> it.trname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                else -> {
                    it.avname.lowercase(Locale.getDefault()).contains(
                        query.value.lowercase(Locale.getDefault())
                    )
                }
            }
        }
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(filteredList) { _, word ->
            WordCard(word)
        }
    }
}