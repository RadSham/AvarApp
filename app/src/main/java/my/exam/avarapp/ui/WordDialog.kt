package my.exam.avarapp.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import my.exam.avarapp.R
import my.exam.avarapp.model.WordEntity


@Composable
fun WordDialog(word: WordEntity, onDismissRequest: () -> Unit) {
    val scrollState = rememberScrollState()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(7.dp),
            shape = RoundedCornerShape(7.dp),
        ) {
            Column(modifier = Modifier.padding(3.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogLanguage(stringResource(id = R.string.avar_lang))
                    DialogWord(word.avname)

                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogLanguage(stringResource(id = R.string.rus_lang))
                    DialogWord(word.rusname)
                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogLanguage(stringResource(id = R.string.eng_lang))
                    DialogWord(word.enname)
                }
                Divider(
                    color = MaterialTheme.colors.secondaryVariant, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogLanguage(stringResource(id = R.string.tr_lang))
                    DialogWord(word.trname)
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
                            Text(
                                modifier = Modifier.padding(bottom = 15.dp),
                                text = word.avexample,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = word.rusexample,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogLanguage(language: String) {
    Text(text = "$language:", textAlign = TextAlign.Left, color = MaterialTheme.colors.primaryVariant)
}

@Composable
fun DialogWord(word: String) {
    Text(
        text = word,
        textAlign = TextAlign.Right
    )
}