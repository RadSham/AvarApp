package my.exam.avarapp.ui.chat

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import my.exam.avarapp.R
import my.exam.avarapp.model.Constants
import my.exam.avarapp.model.MessageEntity
import my.exam.avarapp.ui.theme.RedMain
import kotlin.math.roundToInt

//https://github.com/cp-radhika-s/swipe-to-action-blog-demo/
enum class DragAnchors {
    Start,
    Center,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    state: AnchoredDraggableState<DragAnchors>,
    content: @Composable BoxScope.() -> Unit,
    startAction: @Composable (BoxScope.() -> Unit)? = {},
    endAction: @Composable (BoxScope.() -> Unit)? = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RectangleShape)
    ) {

        endAction?.let {
            endAction()
        }

        startAction?.let {
            startAction()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal),
            content = content
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawerMotionSwipe(
    messageEntity: MessageEntity,
    updateRepliableMessageId: UpdateRepliableMessageId,
    updateRepliableMessageMail: UpdateRepliableMessageMail,
    updateRepliableMessageText: UpdateRepliableMessageText,
    showRepliableMessage: ShowRepliableMessage,
    scrollToMessage: ScrollToMessage
) {
    val density = LocalDensity.current
    val defaultActionSize = 40.dp
    val startActionSizePx = 0f
    val endActionSizePx = with(density) { defaultActionSize.toPx() * 2 }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            anchors = DraggableAnchors {
                DragAnchors.Start at startActionSizePx
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.4f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween()
        )
    }

    DraggableItem(
        state = state,
        content = { SingleMessage(messageEntity, scrollToMessage) },
        startAction = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart),
            ) {
                ReplySwipeBox(
                    Modifier
                        .width(defaultActionSize)
                        .fillMaxHeight()
                        .offset {
                            IntOffset(
                                ((state
                                    .requireOffset()) - defaultActionSize.toPx())
                                    .roundToInt(), 0
                            )
                        },
                    state,
                    messageEntity.message,
                    updateRepliableMessageId,
                    updateRepliableMessageMail,
                    updateRepliableMessageText,
                    showRepliableMessage
                )
            }
        },
    )
}

//reply action box
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReplySwipeBox(
    modifier: Modifier,
    state: AnchoredDraggableState<DragAnchors>,
    message: Map<String, Any>,
    updateRepliableMessageId: UpdateRepliableMessageId,
    updateRepliableMessageMail: UpdateRepliableMessageMail,
    updateRepliableMessageText: UpdateRepliableMessageText,
    showRepliableMessage: ShowRepliableMessage
) {
    Box(
        modifier = modifier.background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(22.dp),
            painter = painterResource(R.drawable.ic_reply_all),
            contentDescription = null,
            tint = RedMain
        )
        ReplyAction(
            state,
            message,
            updateRepliableMessageId,
            updateRepliableMessageMail,
            updateRepliableMessageText,
            showRepliableMessage
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReplyAction(
    state: AnchoredDraggableState<DragAnchors>,
    message: Map<String, Any>,
    updateRepliableMessageId: UpdateRepliableMessageId,
    updateRepliableMessageMail: UpdateRepliableMessageMail,
    updateRepliableMessageText: UpdateRepliableMessageText,
    showRepliableMessage: ShowRepliableMessage
) {
    LaunchedEffect(state.currentValue) {
        if (state.currentValue == DragAnchors.End) {
            updateRepliableMessageId.update(message[Constants.MESSAGE_ID].toString())
            updateRepliableMessageMail.update(message[Constants.USER_EMAIL].toString())
            updateRepliableMessageText.update(message[Constants.MESSAGE].toString())
            showRepliableMessage.show(true)
            state.animateTo(DragAnchors.Start)
        }
    }
}