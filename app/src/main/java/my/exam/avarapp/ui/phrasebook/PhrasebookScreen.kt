package my.exam.avarapp.ui.phrasebook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.exam.avarapp.model.CategoryPhraseEntity


@Composable
fun PhrasebookScreen(
    padding: PaddingValues,
    categoryPhraseEntityListState: MutableState<List<CategoryPhraseEntity>>
) {
    Column(modifier = Modifier.padding(padding)) {
        Text(
            text = "РУССКО-АВАРСКИЙ РАЗГОВОРНИК",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.secondary,
        )
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(categoryPhraseEntityListState.value) { _, categoryPhraseEntity ->
                PhraseCard(categoryPhraseEntity)
            }
        }
    }
}
