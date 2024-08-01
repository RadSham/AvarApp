package my.exam.avarapp.ui.chat

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants
import my.exam.avarapp.model.MessageEntity
import my.exam.avarapp.viewmodel.ChatViewModel

@Composable
fun Chat(
    chatViewModel: ChatViewModel,
    showToast: ShowToast,
) {
    val message: String by chatViewModel.message.observeAsState(initial = "")
    val repliableMessage: String by chatViewModel.repliableMessageText.observeAsState(initial = "")
    val showRepliableMessage: Boolean by chatViewModel.showRepliableMessage.observeAsState(initial = false)
    val messages: List<Map<String, Any>> by chatViewModel.messages.observeAsState(
        initial = emptyList<Map<String, Any>>().toMutableList()
    )
    val chatListState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch
    val chatCoroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.85f, fill = true),
            state = chatListState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            reverseLayout = true
        ) {
            items(messages) { messageItem ->
                val messageEntity = MessageEntity(
                    scrollToMessageIndex = messages.indexOf(messages.find { it[Constants.MESSAGE_ID] == messageItem[Constants.REPLY_TO_ID] }),
                    message = messageItem
                )
                DrawerMotionSwipe(messageEntity, object : UpdateRepliableMessageId {
                    override fun update(messageId: String) {
                        chatViewModel.updateRepliableMessageId(messageId)
                    }
                }, object : UpdateRepliableMessageText {
                    override fun update(messageText: String) {
                        chatViewModel.updateRepliableMessageText(messageText)
                    }
                }, object : ShowRepliableMessage {
                    override fun show(boolean: Boolean) {
                        chatViewModel.updateShowRepliableMessage(boolean)
                    }
                },
                    object : ScrollToMessage {
                        override fun scroll(scrollToMessageIndex: Int) {
                            chatCoroutineScope.launch {
                                chatListState.animateScrollToItem(scrollToMessageIndex)
                            }
                        }
                    }
                )
            }
        }
        //make text selectable
        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            Column(
                Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = RectangleShape
                )
            ) {
                //Repliable message
                if (showRepliableMessage) OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    enabled = false,
                    value = repliableMessage,
                    maxLines = 2,
                    onValueChange = { },
                    trailingIcon = {
                        IconButton(onClick = {
                            chatViewModel.updateRepliableMessageText("")
                            chatViewModel.updateRepliableMessageId("")
                            chatViewModel.updateShowRepliableMessage(false)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close Repliable Message"
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.onSecondary,
                    )
                )
                //New message
                OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f),
                    textColor = Color.Black,
                    unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
                ),
                    value = message,
                    onValueChange = { chatViewModel.updateMessage(it) },
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            chatViewModel.addMessage(showToast, object : ScrollToMessage {
                                override fun scroll(scrollToMessageIndex: Int) {
                                    chatCoroutineScope.launch {
                                        chatListState.scrollToItem(scrollToMessageIndex)
                                    }
                                }
                            })
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Button"
                            )
                        }
                    })
            }
        }
    }
}

@Composable
fun SingleMessage(
    messageEntity: MessageEntity,
    scrollToMessage: ScrollToMessage
) {
    val isCurrentUser = messageEntity.message[Constants.IS_CURRENT_USER] as Boolean
    Box(
        modifier = Modifier
            .fillMaxWidth(),
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
            Column(
                Modifier.border(
                    width = 1.dp,
                    MaterialTheme.colors.secondaryVariant,
                    shape = RectangleShape
                )
            ) {
                if (messageEntity.message[Constants.REPLY_TO_ID] != Constants.ALL) {
                    OutlinedTextField(
                        value = messageEntity.message[Constants.REPLY_TO_TEXT].toString(),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .wrapContentWidth()
                            .border(
                                width = 1.dp,
                                MaterialTheme.colors.onBackground,
                                shape = RectangleShape
                            )
                            .clickable {
                                scrollToMessage.scroll(messageEntity.scrollToMessageIndex)
                            }
                            .align(if (isCurrentUser) Alignment.End else Alignment.Start),
                        enabled = false,
                        maxLines = 1,
                        onValueChange = {},
                        trailingIcon = {},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.onSecondary,
                        )
                    )
                }
                Text(
                    text = messageEntity.message[Constants.USER_EMAIL].toString(),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            start = 10.dp, top = 10.dp, end = 10.dp
                        ),
                    textAlign = if (isCurrentUser) TextAlign.End else TextAlign.Start,
                    fontSize = 10.sp,
                )
                Text(
                    text = messageEntity.message[Constants.MESSAGE].toString(),
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

interface UpdateRepliableMessageText {
    fun update(messageText: String)
}

interface UpdateRepliableMessageId {
    fun update(messageId: String)
}

interface ShowRepliableMessage {
    fun show(boolean: Boolean)
}

interface ScrollToMessage {
    fun scroll(scrollToMessageIndex: Int)
}