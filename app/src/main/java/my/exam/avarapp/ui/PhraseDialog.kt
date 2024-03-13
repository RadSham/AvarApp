package my.exam.avarapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import my.exam.avarapp.model.CategoryPhraseEntity
import my.exam.avarapp.model.PhraseEntity

@Composable
fun PhraseDialog(categoryPhraseEntity: CategoryPhraseEntity, onDismissRequest: () -> Unit) {
    val scrollState = rememberScrollState()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(1.dp),
            shape = RoundedCornerShape(1.dp),
        ) {
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(3.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = categoryPhraseEntity.categoryphrasename,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                val customTextSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
                )

                CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                    SelectionContainer {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            categoryPhraseEntity.categoryphraselist.forEach {
                                PhraseDialogCard(it)
                            }
                        }
                    }

                    /*LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(phraseEntity.categoryphraselist) { phrase ->
                            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                                SelectionContainer {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 3.dp)
                                            .shadow(
                                                2.dp,
                                                ambientColor = MaterialTheme.colors.secondary,
                                                spotColor = MaterialTheme.colors.secondary
                                            ),
                                        shape = RoundedCornerShape(3.dp),
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .border(
                                                    width = 2.dp,
                                                    color = MaterialTheme.colors.secondaryVariant,
                                                    shape = RoundedCornerShape(2.dp)
                                                )
                                                .padding(10.dp)
                                        ) {
                                            Text(
                                                text = phrase.rusphrase,
                                                color = MaterialTheme.colors.onPrimary,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 15.dp),
                                                textAlign = TextAlign.Start
                                            )
                                            Divider(
                                                color = MaterialTheme.colors.secondaryVariant,
                                                modifier = Modifier
                                                    .height(1.dp)
                                                    .fillMaxWidth()
                                            )
                                            Text(
                                                text = phrase.avphrase,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 15.dp),
                                                textAlign = TextAlign.End
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                }
            }
        }
    }
}

@Composable
fun PhraseDialogCard(phraseEntity: PhraseEntity){
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
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(2.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                text = phraseEntity.rusphrase,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                textAlign = TextAlign.Start
            )
            Divider(
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Text(
                text = phraseEntity.avphrase,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                textAlign = TextAlign.End
            )
        }
    }
}


