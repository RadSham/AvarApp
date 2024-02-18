package my.exam.avarapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import my.exam.avarapp.model.WordEntity
import java.util.Locale

@Composable
fun WordsList(
    query: MutableState<String>,
    wordsListState: MutableState<List<WordEntity>>,
    language: String,
) {
    val filteredList =
        wordsListState.value.filter {
            when (language) {
                "Авар мацI" -> {
                    query.value = query.value.replace("[i1l!|Ӏӏ]".toRegex(), "I")
                    it.avderivatives.lowercase(Locale.getDefault())
                        .contains(query.value.lowercase(Locale.getDefault()))
                }

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
        }.partition {
            when (language) {
                "Авар мацI" -> {
                    query.value = query.value.replace("[i1l!|Ӏӏ]".toRegex(), "I")
                    it.avname.lowercase(Locale.getDefault())
                        .startsWith(query.value, ignoreCase = true)
                }

                "Русский язык" -> it.rusname.lowercase(Locale.getDefault())
                    .startsWith(query.value, ignoreCase = true)

                "English" -> it.enname.lowercase(Locale.getDefault())
                    .startsWith(query.value, ignoreCase = true)

                "Turkce" -> it.trname.lowercase(Locale.getDefault())
                    .startsWith(query.value, ignoreCase = true)

                else -> {
                    it.avname.lowercase(Locale.getDefault())
                        .startsWith(query.value, ignoreCase = true)
                }
            }
        }.run { first + second }

    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(filteredList) { _, word ->
            WordCard(word, language)
        }
    }
}