package com.example.avarapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avarapp.R

@Composable
fun SearchView(
    query: MutableState<String>
) {

    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .border(
                1.dp,
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        BasicTextField(
            value = query.value,
            onValueChange = {
                query.value = it
            },
            modifier = Modifier
                .background(
                    Color.White,
                    shape = CircleShape
                )
                .height(38.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.value.isEmpty())
                            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "search_icon")
                        it()
                    }
                    if (query.value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                query.value = ""
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear Icon",
                                tint = Color.Black //Or desired color
                            )
                        }
                    }
                }
            }
        )
    }
}


