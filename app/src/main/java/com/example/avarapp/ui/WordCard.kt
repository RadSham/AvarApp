package com.example.avarapp.ui

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.avarapp.R
import com.example.avarapp.model.WordEntity
import com.example.avarapp.ui.theme.DividerColor
import com.example.avarapp.ui.theme.RedMain

@Composable
fun WordCard(word: WordEntity, language: String) {
    val openDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .shadow(
                2.dp,
                ambientColor = RedMain,
                spotColor = RedMain
            ),
        shape = RoundedCornerShape(3.dp),
        contentColor = Color.DarkGray
    ) {
        Box(modifier = Modifier.clickable(
            onClick = {
                openDialog.value = true
            }
        )) {
            when {
                openDialog.value -> {
                    WordDialog(word,
                        onDismissRequest = { openDialog.value = false }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = DividerColor,
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
                    color = DividerColor,
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
                    color = DividerColor,
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
