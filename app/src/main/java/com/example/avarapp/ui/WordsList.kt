package com.example.avarapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.avarapp.R
import com.example.avarapp.model.WordEntity
import java.util.Locale

@Composable
fun WordsList(
    wordsState: MutableState<List<WordEntity>>,
    query: MutableState<String>,
    language: String,
    dialog: MutableState<Boolean>
) {
    println("Авар мацI" == stringResource(id = R.string.avar_lang))
    val filteredList = wordsState.value
        .filter {
            when (language) {
                "Авар мацI"->
                    it.avname.lowercase(Locale.getDefault()).contains(
                        query.value.lowercase(Locale.getDefault()))

                stringResource(id = R.string.rus_lang) -> it.rusname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                stringResource(id = R.string.eng_lang) -> it.enname.lowercase(Locale.getDefault()).contains(
                    query.value.lowercase(Locale.getDefault())
                )

                stringResource(id = R.string.tr_lang) -> it.trname.lowercase(Locale.getDefault()).contains(
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
            WordCard(word, language)
        }
    }
    ProcessBar(dialog = dialog)
}