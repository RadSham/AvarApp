package my.exam.avarapp.ui.dictionary

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.exam.avarapp.R
import my.exam.avarapp.model.WordEntity

@Composable
fun WordCard(word: WordEntity, language: String) {
    val openWordDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .shadow(
                2.dp,
                ambientColor = MaterialTheme.colors.secondary,
                spotColor = MaterialTheme.colors.secondary
            ),
        shape = RoundedCornerShape(3.dp),
    ) {
        Box(modifier = Modifier.clickable(
            onClick = {
                openWordDialog.value = true
            }
        )) {
            when {
                openWordDialog.value -> {
                    WordDialog(word,
                        onDismissRequest = { openWordDialog.value = false }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(3.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = word.avname,
                        fontWeight = if (language == stringResource(id = R.string.avar_lang)) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = word.rusname,
                        fontWeight = if (language == stringResource(id = R.string.rus_lang)) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = word.enname,
                        fontWeight = if (language == stringResource(id = R.string.eng_lang)) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = word.trname,
                        fontWeight = if (language == stringResource(id = R.string.tr_lang)) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = word.avexample,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
