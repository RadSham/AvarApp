package my.exam.avarapp.ui.dictionary

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
    query.value = query.value.replace("[1!|Ӏӏ]".toRegex(), "I")
    val filteredList =
        wordsListState.value.filter {
            when (language) {
                "Авар мацI" ->
                    it.avderivatives.lowercase(Locale.getDefault())
                        .contains(query.value, ignoreCase = true)

                "Русский язык" -> it.rusname.lowercase(Locale.getDefault())
                    .contains(query.value, ignoreCase = true)

                "English" -> it.enname.lowercase(Locale.getDefault())
                    .contains(query.value, ignoreCase = true)

                "Turkce" -> it.trname.lowercase(Locale.getDefault())
                    .contains(query.value, ignoreCase = true)

                else -> {
                    it.avname.lowercase(Locale.getDefault())
                        .contains(query.value, ignoreCase = true)
                }
            }
        }.partition {
            when (language) {
                "Авар мацI" ->
                    it.avname.lowercase(Locale.getDefault())
                        .startsWith(query.value, ignoreCase = true)

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