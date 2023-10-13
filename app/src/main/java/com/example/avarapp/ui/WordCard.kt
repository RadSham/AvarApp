package com.example.avarapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.avarapp.model.Word

@Composable
fun WordCard(word: Word) {
    val openDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        shape = RoundedCornerShape(3.dp),
        contentColor = Color.DarkGray
    ) {
        Box(modifier = Modifier.clickable(
            onClick = {
                openDialog.value = true
            }
        )) {
            when {
                // ...
                openDialog.value -> {
                    WordDialog(word,
                        onDismissRequest = { openDialog.value = false }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(3.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = word.avname)
                    Text(text = word.rusname)
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = word.enname)
                    Text(text = word.trname)
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = word.avexample,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun WordDialog(word: Word, onDismissRequest: () -> Unit) {
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
                    Text(text = "Авар мацI:", color = Color.DarkGray)
                    Text(text = word.avname, fontWeight = FontWeight.Bold)
                }
                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Русский язык:", color = Color.DarkGray)
                    Text(text = word.rusname, fontWeight = FontWeight.Bold)
                }
                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "English:", color = Color.DarkGray)
                    Text(text = word.enname, fontWeight = FontWeight.Bold)
                }
                Divider(
                    color = Color.LightGray, modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Turkce:", color = Color.DarkGray)
                    Text(text = word.trname, fontWeight = FontWeight.Bold)
                }
                Divider(
                    color = Color.LightGray, modifier = Modifier
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