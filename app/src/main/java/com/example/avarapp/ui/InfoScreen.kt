package com.example.avarapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen() {
    Box(modifier = Modifier.fillMaxSize()
    ) {
        Text(text = infoText(), modifier = Modifier.align(Alignment.Center).padding(10.dp),
            textAlign = TextAlign.Center)
    }
}

fun infoText(): String{
    return "Салам алайкум. \nЭто оффлайн словарь аварского, русского, турецкого и английских языков"
}
