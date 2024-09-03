package my.exam.avarapp.model


data class ChatState(
    val messages: List<MessageItem> = emptyList(),
    val isLoading: Boolean = false
)
