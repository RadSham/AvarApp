package com.example.avarapp.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IndeterminateCircularIndicator() {
    var loading by remember { mutableStateOf(false) }

    Button(onClick = { loading = !loading }) {
        Text("Start loading")
    }

    if (!loading) return

    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colors.secondary
    )
}