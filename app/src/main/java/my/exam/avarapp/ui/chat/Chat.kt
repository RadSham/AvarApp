package my.exam.avarapp.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants
import my.exam.avarapp.viewmodel.ChatViewModel

@Composable
fun Chat(
    chatViewModel: ChatViewModel,
    showToast: ShowToast,
) {
    val message: String by chatViewModel.message.observeAsState(initial = "")
    val messages: List<Map<String, Any>> by chatViewModel.messages.observeAsState(
        initial = emptyList<Map<String, Any>>().toMutableList()
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.85f, fill = true),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            reverseLayout = true
        ) {
            items(messages) { message ->
                val isCurrentUser = message[Constants.IS_CURRENT_USER] as Boolean
                SingleMessage(
                    message = message[Constants.MESSAGE].toString(),
                    userEmail = message[Constants.USER_EMAIL].toString(),
                    isCurrentUser = isCurrentUser
                )
            }
        }
        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.secondary,
                    textColor = Color.Black,
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
                ),
                value = message,
                onValueChange = { chatViewModel.updateMessage(it) },
                maxLines = 3,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 1.dp)
                    .fillMaxWidth()
                    .weight(weight = 0.09f, fill = false),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            chatViewModel.addMessage(showToast)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Button"
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun SingleMessage(message: String, userEmail: String, isCurrentUser: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 48f,
                topEnd = 48f,
                bottomStart = if (isCurrentUser) 48f else 0f,
                bottomEnd = if (isCurrentUser) 0f else 48f
            ),
            backgroundColor = if (isCurrentUser) MaterialTheme.colors.onBackground
            else Color.White,
        ) {
            Column {
                Text(
                    text = userEmail,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            start = 10.dp,
                            top = 10.dp,
                            end = 10.dp
                        ),
                    textAlign = if (isCurrentUser) TextAlign.End else TextAlign.Start,
                    fontSize = 10.sp,
                )
                Text(
                    text = message,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Start,
                    color = if (!isCurrentUser) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}