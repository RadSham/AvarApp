package com.example.avarapp.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.avarapp.model.WordEntity
import com.example.avarapp.ui.theme.DividerColor


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
                    TextWordCardLanguage("Авар мацI")
//                    TextWordCardWord(word.avname)
                    Text(
                        text = word.avname,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right
                    )
                }
                Divider(
                    color = DividerColor, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextWordCardLanguage("Русский язык")
                    TextWordCardWord(word.rusname)
                }
                Divider(
                    color = DividerColor, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextWordCardLanguage("English")
                    TextWordCardWord(word.enname)
                }
                Divider(
                    color = DividerColor, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextWordCardLanguage("Turkce")
                    TextWordCardWord(word.trname)
                }
                Divider(
                    color = DividerColor, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = word.avexample,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun TextWordCardLanguage(language: String) {
    Text(text = "$language:", textAlign = TextAlign.Left)
}

@Composable
fun TextWordCardWord(word: String) {
    Text(
        text = word,
        color = Color.DarkGray,
//        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Right
    )
}