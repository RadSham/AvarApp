package my.exam.avarapp.ui.chat

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.exam.avarapp.R
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.MessageEntity
import my.exam.avarapp.model.MessageItem
import my.exam.avarapp.viewmodel.ChatViewModel

@Composable
fun Chat(
    chatViewModel: ChatViewModel,
    showToast: ShowToast,
) {
    LaunchedEffect(key1 = true) {
        chatViewModel.toastEvent.collectLatest { message ->
            showToast.show(message)
        }
    }
    val message: String by chatViewModel.message.observeAsState(initial = "")
    val repliableMessageText: String by chatViewModel.repliableMessageText.observeAsState(initial = "")
    val repliableMessageMail: String by chatViewModel.repliableMessageUsername.observeAsState(
        initial = ""
    )
    val showRepliableMessage: Boolean by chatViewModel.showRepliableMessage.observeAsState(initial = false)
    val chatMessages: List<MessageItem> by chatViewModel.chatMessages.observeAsState(
        initial = emptyList<MessageItem>().toList()
    )
    val chatListState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch
    val chatCoroutineScope = rememberCoroutineScope()
/*    LifecycleStartEffect(Unit) {
        Log.d("GoogleIO","OnStart: Effect")
        onStopOrDispose {
            Log.d("GoogleIO","OnStopOrDispose: Effect")
        }
    }
    LifecycleResumeEffect(Unit) {
        Log.d("GoogleIO","OnResume: Effect")
        onPauseOrDispose {
            Log.d("GoogleIO","OnPauseOrDispose: Effect")
        }
    }*/
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(weight = 0.85f, fill = true)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = chatListState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                reverseLayout = true
            ) {
                items(chatMessages) { messageItem ->
                    val messageEntity = MessageEntity(
                        scrollToMessageIndex = if (messageItem.replyToId != "all") chatMessages.indexOf(
                            chatMessages.find { it.sentOn == messageItem.replyToId.toLong() }) else null,
                        messageItem = messageItem
                    )
                    DrawerMotionSwipe(messageEntity,
                        object : UpdateRepliableMessageId {
                            override fun update(messageId: String) {
                                chatViewModel.updateRepliableMessageId(messageId)
                            }
                        }, object : UpdateRepliableMessageUsername {
                            override fun update(messageUsername: String) {
                                chatViewModel.updateRepliableMessageUsername(messageUsername)
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
            val firstItemVisible by remember {
                derivedStateOf {
                    chatListState.firstVisibleItemIndex < 5
                }
            }
            if (!firstItemVisible) {
                //"Scroll to bottom" button
                Button(
                    onClick = { chatCoroutineScope.launch { chatListState.scrollToItem(0) } },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .size(50.dp), //avoid the oval shape
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = "icon arrow down"
                    )
                }
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
                if (showRepliableMessage) TextField(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    enabled = false,
                    value = repliableMessageText,
                    label = {
                        Text(
                            repliableMessageMail,
                            fontSize = 10.sp
                        )
                    },
                    maxLines = 2,
                    onValueChange = { },
                    trailingIcon = {
                        IconButton(onClick = {
                            chatViewModel.updateRepliableMessageId("")
                            chatViewModel.updateRepliableMessageText("")
                            chatViewModel.updateRepliableMessageUsername("")
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
                            chatViewModel.addChatMessage(showToast, object : ScrollToMessage {
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

interface UpdateRepliableMessageText {
    fun update(messageText: String)
}

interface UpdateRepliableMessageUsername {
    fun update(messageUsername: String)
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