package my.exam.avarapp.ui.chat

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.exam.avarapp.model.Constants
import my.exam.avarapp.model.MessageEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SingleMessage(
    messageEntity: MessageEntity,
    scrollToMessage: ScrollToMessage
) {
    val isCurrentUser = messageEntity.messageItem.isCurrentUser
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Card(
            modifier = Modifier.padding(
                start = if (isCurrentUser) 40.dp else 0.dp,
                end = if (isCurrentUser) 0.dp else 40.dp
            ),
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
                if (messageEntity.messageItem.replyToId != Constants.ALL) {
                    TextField(
                        value = messageEntity.messageItem.replyToText,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .wrapContentWidth()
                            .border(
                                width = 1.dp,
                                MaterialTheme.colors.onBackground,
                                shape = RectangleShape
                            )
                            .clickable {
                                scrollToMessage.scroll(messageEntity.scrollToMessageIndex!!)
                            }
                            .align(if (isCurrentUser) Alignment.End else Alignment.Start)
                            .defaultMinSize(minWidth = 100.dp),
                        enabled = false,
                        label = {
                            Text(
                                messageEntity.messageItem.replyToUsername,
                                fontSize = 10.sp
                            )
                        },
                        maxLines = 1,
                        onValueChange = {},
                        trailingIcon = {},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.onSecondary,
                        )
                    )
                }
                Text(
                    text = messageEntity.messageItem.userName,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 8.dp, bottom = 5.dp, end = 10.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = if (isCurrentUser) TextAlign.End else TextAlign.Start,
                    fontSize = 10.sp,
                )
                Text(
                    text = messageEntity.messageItem.message,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    textAlign = TextAlign.Start,
                    color = if (!isCurrentUser) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onPrimary
                )
                Text(
                    text = formatMilliseconds(
                        messageEntity.messageItem.sentOn
                    ),
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            start = 8.dp, top = 5.dp, bottom = 8.dp, end = 8.dp
                        )
                        .align(Alignment.End),
                    textAlign = TextAlign.End,
                    fontSize = 10.sp,
                )
            }
        }
    }
}

fun formatMilliseconds(milliseconds: Long): String {
    val format = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
    return format.format(Date(milliseconds))
}