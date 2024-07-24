package my.exam.avarapp.ui.phrasebook

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import my.exam.avarapp.model.CategoryPhraseEntity

@Composable
fun PhraseCard(categoryPhraseEntity: CategoryPhraseEntity) {
    val openPhraseDialog = remember { mutableStateOf(false) }
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
                openPhraseDialog.value = true
            }
        )) {
            when {
                openPhraseDialog.value -> {
                    PhraseDialog(categoryPhraseEntity
                    ) { openPhraseDialog.value = false }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = categoryPhraseEntity.categoryphrasename/*, fontWeight = FontWeight.Bold*/)
            }
        }
    }
}