package my.exam.avarapp.ui.dictionary

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
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.exam.avarapp.R

@Composable
fun SearchView(
    query: MutableState<String>
) {

    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .height(48.dp)
            .border(
                1.dp, color = MaterialTheme.colors.secondaryVariant, shape = CircleShape
            )
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            SelectionContainer {
                BasicTextField(value = query.value,
                    onValueChange = {
                        query.value = it
                    },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colors.primary, shape = CircleShape
                        )
                        .height(38.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 1,
                    decorationBox = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (query.value.isEmpty()) Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "search_icon"
                                )
                                it()
                            }
                            if (query.value.isNotEmpty()) {
                                IconButton(onClick = {
                                    query.value = ""
                                    focusManager.clearFocus()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear Icon",
//                                        tint = MaterialTheme.colors.primaryVariant //Or desired color
                                    )
                                }
                            }
                        }
                    })
            }
        }
    }
}

