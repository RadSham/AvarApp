package com.example.avarapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageChooser(
    expanded: MutableState<Boolean>,
    expanded2: MutableState<Boolean>,
    selectedIndex: MutableState<Int>,
    selectedIndex2: MutableState<Int>,
    items: List<String>
) {

    Box {
        Row(    horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    items[selectedIndex.value], modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clickable(onClick = { expanded.value = true })
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex.value = index
                            expanded.value = false
                        }) {
                            Text(text = s)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    items[selectedIndex2.value], modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded2.value = true })
                )
                DropdownMenu(
                    expanded = expanded2.value,
                    onDismissRequest = { expanded2.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex2.value = index
                            expanded2.value = false
                        }) {
                            Text(text = s)
                        }
                    }
                }
            }
        }
    }
}